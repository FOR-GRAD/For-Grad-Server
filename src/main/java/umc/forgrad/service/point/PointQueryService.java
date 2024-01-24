package umc.forgrad.service.point;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.point.PointResponseDto;
import umc.forgrad.dto.student.StudentRequestDto;

import java.io.IOException;

public interface PointQueryService {

    PointResponseDto.MyPointResponseDto getMyPointList(StudentRequestDto.LoginRequestDto loginRequestDto, int page, HttpSession session) throws IOException;

}
