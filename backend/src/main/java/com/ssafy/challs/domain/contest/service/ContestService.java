package com.ssafy.challs.domain.contest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.challs.domain.contest.dto.request.ContestAwardsRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestCreateRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestParticipantAgreeDto;
import com.ssafy.challs.domain.contest.dto.request.ContestParticipantRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestSearchRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestUpdateRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestUpdateStateRequestDto;
import com.ssafy.challs.domain.contest.dto.response.ContestCreateResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestFindResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestParticipantsResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestSearchResponseDto;

public interface ContestService {

	ContestCreateResponseDto createContest(ContestCreateRequestDto contestRequestDto, MultipartFile contestImage,
		Long memberId);

	void updateContest(ContestUpdateRequestDto contestRequestDto, Long memberId);

	Page<ContestSearchResponseDto> searchContest(ContestSearchRequestDto contestSearchRequestDto, Pageable pageable,
		Integer orderBy);

	ContestFindResponseDto findContest(Long contestId, Long memberId);

	void createContestParticipant(ContestParticipantRequestDto participantRequestDto, Long memberId);

	void deleteContestParticipant(Long contestId, Long memberId);

	ContestParticipantsResponseDto searchContestParticipants(Long contestId, Long memberId);

	void updateContestParticipantsState(ContestParticipantAgreeDto agreeMembers, Long memberId);

	void updateContestState(ContestUpdateStateRequestDto updateStateDto, Long memberId);

	void updateAwardsAndParticipantsState(ContestAwardsRequestDto contestAwardsRequestDto, Long memberId);
}
