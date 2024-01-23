package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.point.PointResponseDto;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.service.point.PointQueryService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointQueryService pointQueryService;

    @PostMapping("careers/point")
    public ApiResponse<PointResponseDto.MyPointResponseDto> getMyPoint(@ModelAttribute StudentRequestDto.LoginRequestDto loginRequestDto, @RequestParam int page, HttpSession session) throws IOException {
        PointResponseDto.MyPointResponseDto myPointList = pointQueryService.getMyPointList(loginRequestDto, page, session);
        return ApiResponse.onSuccess(myPointList);
    }

}
