package com.ssafy.challs.domain.contest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssafy.challs.domain.contest.dto.ContestTeamInfoDto;
import com.ssafy.challs.domain.contest.dto.request.ContestCreateRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestParticipantRequestDto;
import com.ssafy.challs.domain.contest.dto.request.ContestUpdateRequestDto;
import com.ssafy.challs.domain.contest.dto.response.ContestAwardsDto;
import com.ssafy.challs.domain.contest.dto.response.ContestFindResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestParticipantsResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestSearchResponseDto;
import com.ssafy.challs.domain.contest.dto.response.ContestTeamMemberInfoDto;
import com.ssafy.challs.domain.contest.dto.response.ContestTeamResponseDto;
import com.ssafy.challs.domain.contest.entity.Awards;
import com.ssafy.challs.domain.contest.entity.Contest;
import com.ssafy.challs.domain.contest.entity.ContestParticipants;
import com.ssafy.challs.domain.team.entity.Team;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContestMapper {

	@Mapping(source = "team", target = "team")
	@Mapping(source = "contestRequestDto.registrationPeriod.start", target = "contestRegistrationStart")
	@Mapping(source = "contestRequestDto.registrationPeriod.end", target = "contestRegistrationEnd")
	@Mapping(source = "contestRequestDto.contestPeriod.start", target = "contestStart")
	@Mapping(source = "contestRequestDto.contestPeriod.end", target = "contestEnd")
	@Mapping(target = "id", ignore = true)
	Contest contestCreateDtoToContest(ContestCreateRequestDto contestRequestDto, Team team, Character contestState);

	@Mapping(source = "contest", target = "contest")
	@Mapping(target = "id", ignore = true)
	Awards awardsDtoToEntity(ContestAwardsDto contestAwardsDto, Contest contest);

	@Mapping(source = "contestRequestDto.contestId", target = "id")
	@Mapping(source = "team", target = "team")
	@Mapping(source = "contestRequestDto.registrationPeriod.start", target = "contestRegistrationStart")
	@Mapping(source = "contestRequestDto.registrationPeriod.end", target = "contestRegistrationEnd")
	@Mapping(source = "contestRequestDto.contestPeriod.start", target = "contestStart")
	@Mapping(source = "contestRequestDto.contestPeriod.end", target = "contestEnd")
	Contest contestUpdateDtoToContest(ContestUpdateRequestDto contestRequestDto, Team team, Character contestState);

	@Mapping(source = "contest.team.teamName", target = "teamName")
	@Mapping(source = "contest.contestRegistrationStart", target = "registrationPeriod.start")
	@Mapping(source = "contest.contestRegistrationEnd", target = "registrationPeriod.end")
	@Mapping(source = "contest.contestStart", target = "contestPeriod.start")
	@Mapping(source = "contest.contestEnd", target = "contestPeriod.end")
	@Mapping(source = "imageUrl", target = "contestImage")
	@Mapping(source = "contest.id", target = "contestId")
	ContestSearchResponseDto contestToSearchResponseDto(Contest contest, String imageUrl);

	@Mapping(source = "contest.team.id", target = "teamId")
	@Mapping(source = "contest.team.teamName", target = "teamName")
	@Mapping(source = "contest.contestRegistrationStart", target = "registrationPeriod.start")
	@Mapping(source = "contest.contestRegistrationEnd", target = "registrationPeriod.end")
	@Mapping(source = "contest.contestStart", target = "contestPeriod.start")
	@Mapping(source = "contest.contestEnd", target = "contestPeriod.end")
	@Mapping(source = "imageUrl", target = "contestImage")
	@Mapping(source = "contest.id", target = "contestId")
	ContestFindResponseDto contestToFindResponseDto(Contest contest, String imageUrl, List<Awards> contestAwards,
		Boolean isParticipantsLeader, Boolean isOwnerTeamMember, String participantState);

	List<ContestAwardsDto> awardsToDtoList(List<Awards> awardsList);

	@Mapping(source = "id", target = "awardsId")
	ContestAwardsDto awardsToDto(Awards awards);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "contest", target = "contest")
	@Mapping(source = "team", target = "team")
	@Mapping(target = "contestParticipantsState", constant = "W")
	@Mapping(target = "isParticipants", constant = "false")
		// 항상 false로 설정
	ContestParticipants contestParticipantsDtoToEntity(ContestParticipantRequestDto participantRequestDto,
		Contest contest, Team team);

	ContestTeamResponseDto entityToContestTeamResponseDto(ContestTeamInfoDto contestTeamInfoDto,
		List<ContestTeamMemberInfoDto> teamMembers);

	@Mapping(source = "contest.id", target = "contestId")
	ContestParticipantsResponseDto dtoToContestTeamResponseDto(Contest contest, List<ContestTeamResponseDto> teamInfo,
		List<ContestAwardsDto> awards);

}
