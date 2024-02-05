package umc.forgrad.dto.student;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class StudentRequestDto {

    @Getter
    @Builder
    public static class LoginRequestDto {
        private String id;
        private String passwd;
    }

    @Getter
    @Builder
    public static class GradDateRequestDto{
        private LocalDate gradDate; // 졸업 예정일

        private String message; // 응원의 한마디
    }
}
