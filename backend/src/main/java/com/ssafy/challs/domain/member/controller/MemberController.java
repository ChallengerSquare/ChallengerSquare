package com.ssafy.challs.domain.member.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.challs.domain.auth.jwt.dto.SecurityMember;
import com.ssafy.challs.domain.member.dto.request.MemberCreateRequestDto;
import com.ssafy.challs.domain.member.dto.request.MemberUpdateRequestDto;
import com.ssafy.challs.domain.member.dto.response.MemberAwardsCodeResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberContestResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberFindResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberTeamLeaderResponseDto;
import com.ssafy.challs.domain.member.dto.response.MemberTeamResponseDto;
import com.ssafy.challs.domain.member.service.MemberService;
import com.ssafy.challs.global.common.exception.BaseException;
import com.ssafy.challs.global.common.exception.ErrorCode;
import com.ssafy.challs.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "멤버 관리 컨트롤러")
public class MemberController {

	private final MemberService memberService;
	private static final String SUCCESS_STRING = "success";

	@GetMapping("/refresh")
	@Operation(summary = "토큰 재발급 API (O)", description = "refreshToken을 이용해서 토큰을 재발급 하는 API")
	public ResponseEntity<SuccessResponse<String>> createToken(@CookieValue String refreshToken) {
		// refresh token이 없으면 에러
		if (refreshToken == null) {
			throw new BaseException(ErrorCode.EXIST_TOKEN_ERROR);
		}
		HttpHeaders httpHeaders = memberService.createToken(refreshToken);
		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(new SuccessResponse<>(HttpStatus.OK, SUCCESS_STRING));
	}

	@DeleteMapping("/logout")
	@Operation(summary = "로그아웃 API", description = "로그아웃으로 refreshToken과 accessToken을 삭제하는 API")
	public ResponseEntity<SuccessResponse<String>> deleteToken(@CookieValue String refreshToken) {
		HttpHeaders httpHeaders = memberService.deleteRefreshToken(refreshToken);
		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(new SuccessResponse<>(HttpStatus.OK, SUCCESS_STRING));
	}

	@PutMapping("/create")
	@Operation(summary = "회원가입 API (O)", description = "소셜 로그인 후 회원가입을 하는 API")
	public ResponseEntity<SuccessResponse<String>> createMember(@AuthenticationPrincipal SecurityMember securityMember,
		@RequestBody MemberCreateRequestDto memberCreateRequestDto) {
		memberService.createMember(memberCreateRequestDto, securityMember.id());
		return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, SUCCESS_STRING));
	}

	@PutMapping("/update")
	@Operation(summary = "회원수정 API (O)", description = "회원 정보를 수정하는 API")
	public ResponseEntity<SuccessResponse<String>> updateMember(@AuthenticationPrincipal SecurityMember securityMember,
		@RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
		memberService.updateMember(memberUpdateRequestDto, securityMember.id());
		return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, SUCCESS_STRING));
	}

	@GetMapping
	@Operation(summary = "회원조회 API (O)", description = "회원 정보를 조회하는 API")
	public ResponseEntity<SuccessResponse<MemberFindResponseDto>> findMember(
		@AuthenticationPrincipal SecurityMember securityMember) {
		return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, memberService.findMember(securityMember.id())));
	}

	@DeleteMapping
	@Operation(summary = "회원삭제 API", description = "회원을 삭제하는 API")
	public ResponseEntity<SuccessResponse<String>> deleteMember(
		@AuthenticationPrincipal SecurityMember securityMember) {
		memberService.deleteMember(securityMember.id());
		return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, SUCCESS_STRING));
	}

	/**
	 * 멤버가 가입한 팀의 목록 조회
	 *
	 * @author 강태연
	 * @param pageable 페이징 정보
	 * @param securityMember 현재 요청을 한 멤버 정보
	 * @return 팀 정보 목록
	 */
	@GetMapping("/teams")
	@Operation(summary = "팀 목록 조회 API (O)", description = "회원이 가입한 팀의 목록을 조회하는 API")
	public ResponseEntity<SuccessResponse<Page<MemberTeamResponseDto>>> searchTeamList(
		@PageableDefault Pageable pageable,
		@AuthenticationPrincipal SecurityMember securityMember) {
		return ResponseEntity.ok(
			new SuccessResponse<>(HttpStatus.OK, memberService.searchTeamList(securityMember.id(), pageable)));
	}

	/**
	 * 멤버가 가입된 모든 팀이 대회 참가 대기, 대회 참가 승인 상태인 대회(대회 모집 시작, 대회 모집 완료, 대회 시작, 대회 끝)의 목록 조회
	 *
	 * @author 강태연
	 * @param pageable 페이징 정보
	 * @param securityMember 현재 요청 보낸 멤버 정보
	 * @return 팀 정보 목록
	 */
	@GetMapping("/contest")
	@Operation(summary = "대회 목록 조회 (O)", description = "멤버가 포함된 팀이 참가 신청한 대회 목록 조회")
	public ResponseEntity<SuccessResponse<Page<MemberContestResponseDto>>> searchContestList(
		@PageableDefault Pageable pageable,
		@AuthenticationPrincipal SecurityMember securityMember) {
		return ResponseEntity.ok(
			new SuccessResponse<>(HttpStatus.OK, memberService.searchContestList(pageable, securityMember.id())));
	}

	/**
	 * 시상 정보 목록 조회
	 *
	 * @author 강태연
	 * @param securityMember 현재 요청 보낸 멤버 정보
	 * @param pageable 페이징 정보
	 * @return 시상 정보 목록
	 */
	@GetMapping("/awards")
	@Operation(summary = "대회 내역 조회 (O)", description = "멤버가 가입한 모든 팀이 참여한 대회 목록을 조회하는 API")
	public ResponseEntity<SuccessResponse<Page<MemberAwardsCodeResponseDto>>> searchAwardList(
		@PageableDefault Pageable pageable, @AuthenticationPrincipal SecurityMember securityMember) {
		return ResponseEntity.ok(
			new SuccessResponse<>(HttpStatus.OK, memberService.searchAwardList(securityMember.id(), pageable)));
	}

	/**
	 * 멤버이 가입한 팀 중에서 리더인 팀의 목록을 조회
	 *
	 * @author 강태연
	 * @param securityMember 현재 요청 보낸 멤버 정보
	 * @return 팀 정보 목록
	 */
	@GetMapping("/teams/leader")
	@Operation(summary = "리더인 팀 목록 조회 API (O)", description = "회원이 가입한 팀 중에서 리더인 팀의 목록을 조회하는 API")
	public ResponseEntity<SuccessResponse<List<MemberTeamLeaderResponseDto>>> searchTeamLeaderList(
		@AuthenticationPrincipal SecurityMember securityMember) {
		return ResponseEntity.ok(
			new SuccessResponse<>(HttpStatus.OK, memberService.searchTeamLeaderList(securityMember.id())));
	}

}
