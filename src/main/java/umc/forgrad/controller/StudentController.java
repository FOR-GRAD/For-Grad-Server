package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.student.StudentCommandServiceImpl;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentCommandServiceImpl studentCommandService;

    @PostMapping("/login")
    public ApiResponse<StudentResponseDto.LoginResponseDto> login(@ModelAttribute StudentRequestDto.LoginRequestDto loginRequestDto) {
        String loginResultMessage = studentCommandService.login(loginRequestDto);
        return ApiResponse.onSuccess(StudentConverter.toLoginResultDto(loginResultMessage));
    }

}
