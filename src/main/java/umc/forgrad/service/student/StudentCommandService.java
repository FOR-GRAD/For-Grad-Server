package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.student.StudentRequestDto;

import java.io.IOException;

public interface StudentCommandService {

    String login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException;

}
