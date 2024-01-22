package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.student.StudentResponseDto;

import java.io.IOException;

public interface StudentQueryService {

    StudentResponseDto.HomeResponseDto queryHome(HttpSession session) throws IOException;

}
