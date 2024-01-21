package umc.forgrad.dto.student;

import lombok.Builder;
import lombok.Getter;

public class StudentRequestDto {

    @Getter
    @Builder
    public static class LoginRequestDto {
        private String id;
        private String passwd;
    }

}
