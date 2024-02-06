package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentResponseDto;

import java.time.LocalDate;

public class StudentConverter {

    public static Student toStudent(Long id, String track1, String track2) {
        return Student.builder()
                .id(id)
                .track1(track1)
                .track2(track2)
                .gradDate(LocalDate.now())
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
