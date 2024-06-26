package com.ssafy.challs.domain.alert.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.challs.domain.alert.dto.response.AlertResponseDto;
import com.ssafy.challs.domain.alert.entity.Alert;
import com.ssafy.challs.domain.alert.entity.AlertMember;
import com.ssafy.challs.domain.alert.repository.AlertMemberRepository;
import com.ssafy.challs.domain.alert.repository.AlertRepository;
import com.ssafy.challs.domain.alert.service.impl.AlertServiceImpl;
import com.ssafy.challs.global.common.exception.BaseException;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {
	@Mock
	private AlertRepository alertRepository;
	@Mock
	private AlertMemberRepository alertMemberRepository;
	@InjectMocks
	private AlertServiceImpl alertService;

	@Test
	@DisplayName("알림_생성_성공")
	void testCreateAlert() {
		// 테스트 데이터 준비
		List<Long> receivers = Arrays.asList(1L, 2L);
		Alert alert = new Alert(1L, 'N', "Content", 1L);
		when(alertRepository.save(any(Alert.class))).thenReturn(alert);

		// 테스트 실행
		alertService.createAlert(receivers, 'N', 1L, "Content");

		// 검증
		verify(alertRepository).save(any(Alert.class));
		verify(alertMemberRepository, times(receivers.size())).save(any(AlertMember.class));
	}

	@Test
	@DisplayName("알림 조회 테스트")
	void testFindAlerts() {
		// 테스트 데이터 준비
		AlertMember alertMember = AlertMember.builder()
			.alert(
				Alert.builder().id(1L).alertTargetId(1L).alertType('N').alertContent("알림 내용").build())
			.memberId(1L)
			.isRead(false).build();
		List<AlertMember> alertMembers = Arrays.asList(alertMember);
		when(alertMemberRepository.findAllByMemberId(1L)).thenReturn(alertMembers);

		// 테스트 실행
		List<AlertResponseDto> results = alertService.findAlerts(1L, false);

		// 검증
		assertEquals(1, results.size());
		assertEquals("알림 내용", results.get(0).alertContent());
	}

	@Test
	@DisplayName("읽음 상태 업데이트 테스트")
	void testUpdateAlert() {
		// 테스트 데이터 준비
		Alert alert = new Alert(1L, 'N', "알림 내용", 1L);
		AlertMember alertMember = AlertMember.builder().alert(alert).memberId(1L).isRead(false).build();
		when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));
		when(alertMemberRepository.findAlertMembersByMemberIdAndAlert(1L, alert)).thenReturn(
			Optional.of(alertMember));

		// 테스트 실행
		alertService.updateAlert(1L, 1L);

		// 검증
		assertTrue(alertMember.getIsRead());
		verify(alertMemberRepository).findAlertMembersByMemberIdAndAlert(1L, alert);
	}

	@Test
	@DisplayName("알림 ID로 찾기 실패 시 예외 발생 테스트")
	void testUpdateAlertNotFound() {
		// 준비
		when(alertRepository.findById(anyLong())).thenReturn(Optional.empty());

		// 실행 & 검증
		assertThrows(BaseException.class, () -> alertService.updateAlert(1L, 1L));
	}
}