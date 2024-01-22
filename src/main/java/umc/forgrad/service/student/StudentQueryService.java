package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.student.StudentResponseDto;

public interface StudentQueryService {

    StudentResponseDto.HomeResponseDto queryHome(HttpSession session);


}
