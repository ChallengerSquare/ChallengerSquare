package com.ssafy.challs.domain.contest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContestTeamInfoDto(
	@Schema(description = "팀 PK", example = "1")
	Long teamId,
	@Schema(description = "팀 이름", example = "버저비터")
	String teamName,
	@Schema(description = "수락 여부", example = "A")
	String contestParticipantsState,
	@Schema(description = "참석 여부", example = "true")
	Boolean isParticipants,
	@Schema(description = "신청 사유", example = "대회 참가하고 싶어요")
	String contestParticipantsReason
) {
}
