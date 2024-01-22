package umc.forgrad.service.student;

import umc.forgrad.dto.student.StudentRequestDto;

import java.io.IOException;

public interface StudentCommandService {

    String login(StudentRequestDto.LoginRequestDto loginRequestDto) throws IOException;

}
