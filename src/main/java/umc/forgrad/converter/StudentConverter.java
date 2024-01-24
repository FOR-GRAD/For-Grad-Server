package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentResponseDto;

public class StudentConverter {

    public static Student toStudent(Long id) {
        return Student.builder()
                .id(id)
                .build();
    }

    public static StudentResponseDto.LoginResponseDto toLoginResultDto(String loginResultMessage) {
        return StudentResponseDto.LoginResponseDto.builder()
                .message(loginResultMessage)
                .build();
    }

    public static StudentResponseDto.LogoutResponseDto toLogoutResultDto(String logoutResultMessage) {
        return StudentResponseDto.LogoutResponseDto.builder()
                .message(logoutResultMessage)
                .build();
    }

}
