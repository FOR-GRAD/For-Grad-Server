package umc.forgrad.converter;

import umc.forgrad.dto.student.StudentResponseDto;

public class StudentConverter {

    public static StudentResponseDto.LoginResponseDto toLoginResultDto(String loginResultMessage) {
        return StudentResponseDto.LoginResponseDto.builder()
                .message(loginResultMessage)
                .build();
    }

}
