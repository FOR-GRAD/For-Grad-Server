package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.point.PointResponseDto;
import umc.forgrad.service.point.PointQueryService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointQueryService pointQueryService;

    @GetMapping("careers/point")
    public ApiResponse<PointResponseDto.MyPointResponseDto> getMyPoint(@RequestParam int page, HttpSession session) throws IOException {
        PointResponseDto.MyPointResponseDto myPointList = pointQueryService.getMyPointList(page, session);
        return ApiResponse.onSuccess(myPointList);
    }

}
