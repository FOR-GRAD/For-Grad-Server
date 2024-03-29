package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.student.StudentCommandService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentCommandService studentCommandService;

    @PostMapping("/login")
    public ApiResponse<StudentResponseDto.LoginResponseDto> login(@ModelAttribute StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException {
        Student student = studentCommandService.login(loginRequestDto, session);
        session.setAttribute("student", student.getId());
        return ApiResponse.onSuccess(StudentConverter.toLoginResultDto("login success"));
    }

    @GetMapping("/logout")
    public ApiResponse<StudentResponseDto.LogoutResponseDto> logout(HttpSession session) throws IOException {
        String logoutResultMessage = studentCommandService.logout(session);
        return ApiResponse.onSuccess(StudentConverter.toLogoutResultDto(logoutResultMessage));
    }

}
