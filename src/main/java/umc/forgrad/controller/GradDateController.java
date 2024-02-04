package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.student.GradDateService;

@RestController
@RequiredArgsConstructor
public class GradDateController {

    private final GradDateService gradDateService;

    @PatchMapping("/grad-date")
    public ApiResponse<StudentResponseDto.GradDateResponseDto> update(@RequestBody StudentRequestDto.GradDateRequestDto gradDateRequestDto, @SessionAttribute(name="student") Long stuId){

        return ApiResponse.onSuccess(gradDateService.updateGradDate(gradDateRequestDto, stuId));
    }

    @GetMapping("/grad-date")
    public ApiResponse<StudentResponseDto.GradDateResponseDto> getGradDate(@SessionAttribute(name="student") Long stuId){

        return ApiResponse.onSuccess(gradDateService.findGradDate(stuId));
    }
}
