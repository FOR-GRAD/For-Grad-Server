package umc.forgrad.dto.student;

import lombok.Builder;
import lombok.Getter;

public class StudentResponseDto {

    @Getter
    @Builder
    public static class LoginResponseDto {
        private String message;
    }

    @Getter
    @Builder
    public static class HomeResponseDto {
        private String name;

        private String track1;

        private String track2;

        private String status; // 재학중, 휴학중과 같은 상태

        private String message; // 응원의 한마디

        private String trackRequirement1; // 1트랙 졸업요건

        private String trackRequirement2; // 2트랙 졸업요건
    }

    @Getter
    @Builder
    public static class FutureTimeTableDto {
        private String majorType; // 전공선택, 전공필수, 전공기초 ...

        private String subject; // 교과목

        private Integer grades; // 학점
    }

}
