package com.ssafy.challs.domain.notice.dto.response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoticeResponseDto(
	@Schema(description = "공지 제목", example = "공지 제목1")
	String title,
	@Schema(description = "공지 내용", example = "내용1")
	String content,
	@Schema(description = "공지 작성일", example = "2024-05-16")
	LocalDate date
) {
}
