package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.service.student.GradDateService;

@RestController
@RequiredArgsConstructor
public class GradDateController {

    private final GradDateService gradDateService;

    @PostMapping("/grad-date")
    public ApiResponse<StudentResponseDto.GradDateResponseDto> create(@RequestBody StudentRequestDto.GradDateRequestDto gradDateRequestDto, @SessionAttribute(name="student") Long stuId){
        return ApiResponse.onSuccess(gradDateService.addGradDate(gradDateRequestDto, stuId));
    }


}
