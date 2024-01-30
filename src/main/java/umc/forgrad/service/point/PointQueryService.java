package umc.forgrad.service.point;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.point.PointResponseDto;

import java.io.IOException;

public interface PointQueryService {

    PointResponseDto.MyPointResponseDto getMyPointList(int page, HttpSession session) throws IOException;

}