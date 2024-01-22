package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.student.StudentCommandServiceImpl;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentCommandServiceImpl studentCommandService;

    @PostMapping("/login")
    public ApiResponse<StudentResponseDto.LoginResponseDto> login(@ModelAttribute StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException {
        String loginResultMessage = studentCommandService.login(loginRequestDto, session);
        return ApiResponse.onSuccess(StudentConverter.toLoginResultDto(loginResultMessage));
    }

}
