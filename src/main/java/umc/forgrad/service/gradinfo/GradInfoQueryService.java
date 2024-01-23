package umc.forgrad.service.gradinfo;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.io.IOException;

public interface GradInfoQueryService {

    GradInfoResponseDto.GradRequirementDto getGradRequirements(HttpSession session) throws IOException;

}
