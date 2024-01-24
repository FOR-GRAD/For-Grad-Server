package umc.forgrad.service.info;

import jakarta.servlet.http.HttpSession;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.io.IOException;

public interface GradesQueryService {

    GradInfoResponseDto.MyGradesInfoDto queryGrades(HttpSession session) throws IOException;

}
