package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;

import java.io.IOException;

public interface StudentCommandService {

    Student login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session);

    String logout(HttpSession session) throws IOException;

}
