package umc.forgrad.service.info;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import umc.forgrad.converter.GradInfoConverter;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class GradInfoQueryServiceImpl implements GradInfoQueryService {

    @Override
    public GradInfoResponseDto.GradRequirementDto getGradRequirements(HttpSession session) throws IOException {

        // 공통 졸업요건 조회
        String commonGradUrl = "https://hansung.ac.kr/eduinfo/3820/subview.do";
        Connection.Response commonGradResponse = getResponse(session, commonGradUrl);

        Document commonGradDocument = commonGradResponse.parse();
        String registration = commonGradDocument.select("#menu3820_obj394 > div > div > ul > li:nth-child(1)").text();
        String grades = commonGradDocument.select("#menu3820_obj394 > div > div > ul > li:nth-child(2)").text();
        String point = commonGradDocument.select("#menu3820_obj394 > div > div > ul > li:nth-child(3)").text();
        String scores = commonGradDocument.select("#menu3820_obj394 > div > div > ul > li:nth-child(4)").text();

        // 졸업요건 조회
        String gradUrl = "https://info.hansung.ac.kr/jsp_21/student/graduation/graduation_requirement.jsp";
        Connection.Response gradResponse = getResponse(session, gradUrl);

        Document gradDocument = gradResponse.parse();
        String track1 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2)").text();
        String track2 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(3) > td:nth-child(2)").text();
        String trackRequirement1 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(2) > td:nth-child(3)").text();
        String trackRequirement2 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(3) > td:nth-child(3)").text();
        String note1 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(2) > td:nth-child(4)").text();
        String note2 = gradDocument.select("#div_print_area > div > div._obj._objHtml._absolute > ul > div > div > div > table > tbody > tr:nth-child(3) > td:nth-child(4)").text();

        GradInfoResponseDto.CommonRequirmentsDto commonDto = GradInfoResponseDto.CommonRequirmentsDto.builder()
                .registration(registration)
                .grades(grades)
                .point(point)
                .scores(scores)
                .build();

        GradInfoResponseDto.TrackRequirmentsDto trackDto = GradInfoResponseDto.TrackRequirmentsDto.builder()
                .track1(track1)
                .track2(track2)
                .trackRequirement1(trackRequirement1)
                .trackRequirement2(trackRequirement2)
                .note1(note1)
                .note2(note2)
                .build();

        return GradInfoConverter.toGradRequirementDto(commonDto, trackDto);

    }

    private static Connection.Response getResponse(HttpSession session, String url) throws IOException {

        @SuppressWarnings(value = "unchecked")
        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        return Jsoup.connect(url)
                .cookies(cookies)
                .method(Connection.Method.GET)
                .execute();

    }

}
