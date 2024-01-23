package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;
import umc.forgrad.service.info.GradInfoQueryService;
import umc.forgrad.service.info.GradesQueryService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gradinfo")
public class GradInfoController {

    private final GradInfoQueryService gradInfoQueryService;
    private final GradesQueryService gradesQueryService;

    @GetMapping("/requirments")
    public ApiResponse<GradInfoResponseDto.GradRequirementDto> getGradRequirments(HttpSession session) throws IOException {
        GradInfoResponseDto.GradRequirementDto gradRequirements = gradInfoQueryService.getGradRequirements(session);
        return ApiResponse.onSuccess(gradRequirements);
    }

    @GetMapping("/grades")
    public ApiResponse<GradInfoResponseDto.MyGradesInfoDto> getGrades(HttpSession session) throws IOException {
        GradInfoResponseDto.MyGradesInfoDto myGradesInfoDtoList = gradesQueryService.queryGrades(session);
        return ApiResponse.onSuccess(myGradesInfoDtoList);
    }

}
