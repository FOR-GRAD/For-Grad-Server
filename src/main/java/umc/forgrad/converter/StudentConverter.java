package umc.forgrad.converter;

import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;

public class StudentConverter {

    // id, passwd를 Dto로 반환
    public static StudentRequestDto.LoginRequestDto toLoginForm(StudentRequestDto.LoginRequestDto loginRequestDto) {
        return StudentRequestDto.LoginRequestDto.builder()
                .id(loginRequestDto.getId())
                .passwd(loginRequestDto.getPasswd())
                .build();
    }

    public static StudentResponseDto.LoginResponseDto toLoginResultDto(String loginResultMessage) {
        return StudentResponseDto.LoginResponseDto.builder()
                .message(loginResultMessage)
                .build();
    }

}
