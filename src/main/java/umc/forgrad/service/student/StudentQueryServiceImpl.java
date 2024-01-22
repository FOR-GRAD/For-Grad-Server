package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import umc.forgrad.dto.student.StudentResponseDto;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentQueryServiceImpl implements StudentQueryService {

    @Override
    public StudentResponseDto.HomeResponseDto queryHome(HttpSession session) throws IOException {
        // 이름, 학번, 학부, 학년, 재학상태 조회
        String gradesUrl = "https://info.hansung.ac.kr/jsp_21/student/grade/total_grade.jsp";

        Connection.Response gradesResponse = getResponse(session, gradesUrl);

        Document document = gradesResponse.parse();
        String text = Objects.requireNonNull(document.select("strong.objHeading_h3").first()).text(); // 황준현 (1871456) 컴퓨터공학부 4 학년 일반휴학

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

        log.info(track1);
        log.info(track2);
        log.info(trackRequirement1);
        log.info(trackRequirement2);
        log.info(note1);
        log.info(note2);

        return null;
    }

    private static Connection.Response getResponse(HttpSession session, String url) throws IOException {

        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        return Jsoup.connect(url)
                .cookies(cookies)
                .method(Connection.Method.GET)
                .execute();

    }

}
