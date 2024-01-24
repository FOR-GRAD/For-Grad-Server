package umc.forgrad.service.info;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.io.IOException;

public interface CompletionQueryService {

    GradInfoResponseDto.CompletionStatusDto getCompletionStatus(HttpSession session) throws IOException;

}
