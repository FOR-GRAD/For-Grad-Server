package umc.forgrad.dto.student;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

        private Integer dDay; // 졸업까지 남은 날짜

        private String base64Image; // 종정시 사진

        private Map<String, FutureTimeTableDto> futureTimeTableDto; // key: 학년학기
    }

    @Getter
    @Builder
    public static class GradDateAndMessageResponseDto {
        private LocalDate nowDate; // 현재 날짜

        private LocalDate gradDate; // 졸업 예정일

        private int dDay; // d-day 계산

        private String message; // 응원의 한마디
    }

    @Getter
    @Builder
    public static class GradUpdatedResponseDto {
        private LocalDate gradDate;

        private String message;
    }


    @Getter
    @Builder
    public static class FutureTimeTableDto {
        private Integer sumCredits;

        private List<TimeTableDto> timeTableDtoList;
    }

    @Getter
    @Builder
    public static class TimeTableDto {
        private String majorType; // 전공선택, 전공필수, 전공기초 ...

        private String subject; // 교과목

        private Integer grades; // 학점
    }

}
