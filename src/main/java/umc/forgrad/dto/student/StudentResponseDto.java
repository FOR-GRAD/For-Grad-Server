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
    public static class LogoutResponseDto {
        private String message;
    }

}
