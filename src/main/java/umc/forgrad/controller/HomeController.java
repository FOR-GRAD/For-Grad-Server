package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.home.HomeQueryService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeQueryService homeQueryService;

    @GetMapping("/home")
    public ApiResponse<StudentResponseDto.HomeResponseDto> home(HttpSession session) throws IOException {
        StudentResponseDto.HomeResponseDto homeResponseDto = homeQueryService.queryHome(session);
        return ApiResponse.onSuccess(homeResponseDto);
    }

}
