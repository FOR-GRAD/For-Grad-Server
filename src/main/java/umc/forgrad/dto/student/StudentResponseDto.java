package umc.forgrad.dto.student;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class StudentResponseDto {

    @Getter
    @Builder
    public static class LoginResponseDto {
        private String message;
    }

    @Getter
    @Builder
    public static class LogoutResponseDto {
        private String message;
    }

    @Getter
    @Builder
    public static class HomeResponseDto {
        private String name; // 내 이름

        private Integer id; // 학번

        private String department; // 학부

        private String grade; // 학년

        private String status; // 재학중, 휴학중과 같은 상태

        private String message; // 응원의 한마디

        private String track1; // 1트랙 이름

        private String track2; // 2트랙 이름

        private String trackRequirement1; // 1트랙 졸업요건

        private String trackRequirement2; // 2트랙 졸업요건

        private String note1; // 1트랙 졸업요건 비고

        private String note2; // 2트랙 졸업요건 비고

        private List<FutureTimeTableDto> futureTimeTableDto;
    }

    @Getter
    @Builder
    public static class FutureTimeTableDto {
        private String majorType; // 전공선택, 전공필수, 전공기초 ...

        private String subject; // 교과목

        private Integer grades; // 학점
    }

}
