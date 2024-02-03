package umc.forgrad.apipayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.forgrad.apipayload.code.BaseCode;
import umc.forgrad.apipayload.code.ReasonDto;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    // 에러 응답
    INTERNER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "서버 에러"),

    // 로그인 실패
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "LOGIN FAIL", "아이디 또는 비밀번호를 확인하세요"),

    // 세션 에러
    SESSION_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "유효하지 않은 세션입니다."),

    // 학생 관련
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "STUDENT NOT FOUNT", "존재하지 않는 학생입니다."),

    // 학기 관련
    SEMESTER_NOT_FOUND(HttpStatus.NOT_FOUND, "SEMESTER NOT FOUND", "존재하지 않는 학기 입니다."),

    // 과목 관련
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBJECT NOT FOUND", "존재하지 않는 과목입니다."),

    //활동
    ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ACTIVITY4001","해당 activityid로 커리어를 찾을수 없습니다"),

    //카테고리별리스트
    ACTIVITYLIST_EMPTY(HttpStatus.NO_CONTENT, "ACTIVITYLIST EMPTY", "아무 활동도 등록되어있지않습니다."),

    NOTICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON001", "서버 자료 부족으로 해당 트랙은 게시판을 제공하지 않습니다."),

    //작성자가 아닌 사람이 글 수정, 삭제를 시도.
    USER_WITHOUT_PERMISSION(HttpStatus.UNAUTHORIZED, "USER WITHOUT PERMISSION", "해당 활동을 관리할 권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }

}