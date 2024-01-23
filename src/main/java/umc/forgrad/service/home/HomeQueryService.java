package umc.forgrad.service.home;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.student.StudentResponseDto;

import java.io.IOException;

public interface HomeQueryService {

    StudentResponseDto.HomeResponseDto queryHome(HttpSession session) throws IOException;

}