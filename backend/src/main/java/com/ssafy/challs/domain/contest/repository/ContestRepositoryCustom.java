package com.ssafy.challs.domain.contest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.challs.domain.contest.dto.request.ContestSearchRequestDto;
import com.ssafy.challs.domain.contest.entity.Contest;
import com.ssafy.challs.domain.member.dto.response.MemberContestResponseDto;
import com.ssafy.challs.domain.team.dto.response.TeamContestResponseDto;

public interface ContestRepositoryCustom {

	String findContestImageByContestId(Long contestId);

	Page<Contest> searchContest(ContestSearchRequestDto searchRequestDto, Pageable pageable, Integer orderBy);

	Page<TeamContestResponseDto> searchTeamContestList(Long teamId, Pageable pageable);

	Page<Contest> searchContestOrderByRegistrationEnd(Pageable pageable);

	Page<Contest> searchContestOrderByRegistrationNum(Pageable pageable);

	Long findTeamIdByContestId(Long contestId);

	void updateContestState(Long contestId, Character contestState);

	Page<MemberContestResponseDto> searchContestList(Pageable pageable, Long memberId);

	String findContestTitleFromContestId(Long contestId);
}
