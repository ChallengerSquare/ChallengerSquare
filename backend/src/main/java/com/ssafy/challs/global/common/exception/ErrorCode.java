package com.ssafy.challs.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/**
	 * HTTP Status Code
	 * 400 : Bad Request
	 * 401 : Unauthorized
	 * 403 : Forbidden
	 * 404 : Not Found
	 * 500 : Internal Server Error
	 *
	 */

	// 잘못된 서버 요청
	BAD_REQUEST_ERROR(400, "G-001", "Bad Request Exception"),

	// @RequestBody 데이터 미 존재
	REQUEST_BODY_MISSING_ERROR(400, "G-002", "Required request body is missing"),

	// 유효하지 않은 타입
	INVALID_TYPE_VALUE(400, "G-003", " Invalid Type Value"),

	// Request Parameter 로 데이터가 전달되지 않을 경우
	MISSING_REQUEST_PARAMETER_ERROR(400, "G-004", "Missing RequestParameter Exception"),

	// 입력/출력 값이 유효하지 않음
	IO_ERROR(400, "G-005", "I/O Exception"),

	// com.google.gson JSON 파싱 실패
	JSON_PARSE_ERROR(400, "G-006", "JsonParseException"),

	// com.fasterxml.jackson.core Processing Error
	JACKSON_PROCESS_ERROR(400, "G-007", "com.fasterxml.jackson.core Exception"),

	// 권한이 없음
	FORBIDDEN_ERROR(403, "G-008", "Forbidden Exception"),

	// 서버로 요청한 리소스가 존재하지 않음
	NOT_FOUND_ERROR(404, "G-009", "Not Found Exception"),

	// NULL Point Exception 발생
	NULL_POINT_ERROR(404, "G-010", "Null Point Exception"),

	// @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
	NOT_VALID_ERROR(400, "G-011", "handle Validation Exception"),

	// Header 값이 없음
	NOT_VALID_HEADER_ERROR(400, "G-012", "Missing Header Exception"),

	// Token 기간 만료
	EXPIRED_TOKEN_ERROR(401, "G-013", "Expired Token Exception"),

	// 잘못된 Token
	SECURITY_TOKEN_ERROR(401, "G-014", "Security Token Exception"),

	// 지원하지 않은 Token
	UNSUPPORTED_TOKEN_ERROR(401, "G-015", "Unsupported Token Exception"),

	// 잘못된 Token
	WRONG_TOKEN_ERROR(401, "G-016", "Wrong Token Exception"),

	// 사용자의 Token이 아님
	NOT_MATCH_TOKEN_ERROR(401, "G-017", "Not Match Token Exception"),

	// 이미 토큰을 발행함
	EXIST_TOKEN_ERROR(401, "G-018", "Exist Token Exception"),

	// Method Not Allowed
	METHOD_NOT_ALLOWED_ERROR(405, "G-020", "Method Not Allowed Exception"),

	// SSE 연결 오류
	SSE_CONNECTION_ERROR(400, "G-021", "SSE Connection Error"),

	// SSE 전송 오류
	SSE_SEND_ERROR(500, "G-022", "SSE Alert Send Error"),

	// 회원의 알림 아님
	ALERT_NOT_OWNER(400, "G-023", "Alert Not Owner"),
	NOT_FOUND_ALERT(400, "G-024", "Not Found Alert"),

	// 멤버의 정보가 존재하지 않음
	MEMBER_FOUND_ERROR(404, "G-025", "Member Found Exception"),

	// 멤버가 약관에 동의하지 않음
	MEMBER_NOT_AGREE_ERROR(403, "G-026", "Member Not Agree Exception"),

	// 해당 대회 찾을 수 없음
	CONTEST_NOT_FOUND_ERROR(404, "G-027", "Contest Not Found Exception"),

	// 팀을 찾을 수 없음
	TEAM_FOUND_ERROR(404, "G-028", "Team Found Exception"),

	// 현재 멤버가 리더가 아님
	MEMBER_NOT_LEADER(403, "G-029", "Member Not Leader"),

	// 현재 멤버가 리더임
	MEMBER_IS_LEADER(403, "G-030", "Member IS Leader"),

	// 이미 참가 신청을 했음
	MEMBER_EXISTS_PARTICIPANTS(400, "G-031", "Member Exists Participants"),

	// 참가 신청 정보가 없음
	PARTICIPANTS_NOT_EXISTS(404, "G-032", "Participant Not Exists"),

	// 대회 모집 중 아님
	CONTEST_NOT_OPEN_ERROR(400, "G-033", "Contest Not Open Exception"),

	// 이미 참여 신청 중인 멤버 존재
	CONTEST_ALREADY_PARTICIPANTS_ERROR(400, "G-034", "Exists Already Participants Member"),

	// 개최팀에 속한 회원이 아닌 경우
	MEMBER_NOT_IN_TEAM(400, "G-035", "Member Is Not In Team"),

	// 로그인이 필요한 접근의 경우
	MEMBER_NOT_LOGIN(401, "G-036", "Member Is Not Login"),

	// 시상 정보가 없음
	AWARDS_CODE_ERROR(404, "G-037", "Awards Code Error"),

	// 이미 참여 / 수상 정보 등록되어 있는 경우
	ALREADY_DECIDED(400, "G-038", "Awards State Already Decided"),

	// 저장된 수상 정보와 다른 값으로 들어왔을 경우
	AWARD_INFO_MISMATCH(400, "G-039", "Awards Info Mismatch"),

	// 저장된 수상 정보와 다른 값으로 들어왔을 경우
	QNA_NOT_FOUND(400, "G-040", "Qna Not Found"),

	// 저장된 수상 정보와 다른 값으로 들어왔을 경우
	LEADER_NOT_FOUND(404, "G-041", "Leader Not Found"),

	// 대회 개최자가 참여신청했을 경우
	MEMBER_OWNER_ERROR(400, "G-042", "Member Is In Contest Owner Team"),

	// 블록체인 서버와의 통신에 실패
	FEIGN_ERROR(500, "G-043", "Feign Error"),

	// 서버가 처리 할 방법을 모르는 경우 발생,
	INTERNAL_SERVER_ERROR(500, "G-999", "Internal Server Error Exception");

	private final int status;
	private final String code;
	private final String message;

}
