package umc.forgrad.service.info;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import umc.forgrad.converter.GradInfoConverter;
import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.io.IOException;
import java.util.*;

import static umc.forgrad.service.common.ConnectionResponse.getResponse;

@Service
@Slf4j
public class CompletionQueryServiceImpl implements CompletionQueryService {

    @Override
    public GradInfoResponseDto.CompletionStatusDto getCompletionStatus(HttpSession session) throws IOException {

        return getCompletionDto(session);

    }

    private static GradInfoResponseDto.CompletionStatusDto getCompletionDto(HttpSession session) throws IOException {

        List<String> titleList = new ArrayList<>();
        List<Map<String, List<String>>> mapList = new ArrayList<>();

        String gradesUrl = "https://info.hansung.ac.kr/jsp_21/student/grade/total_grade.jsp";
        Connection.Response gradesResponse = getResponse(session, gradesUrl);

        Document gradesDoc = gradesResponse.parse();
        Elements divs = gradesDoc.select("#div_print_area > div > div._obj._objHtml._absolute > div > div > div:nth-child(4) > div > div.card-body.div_notice_content > div"); // <div class="row">

        for (Element element : divs) {
            // 교양 div와 전공 div를 나눠서 가져옴
            for (Element div : element.children()) {

                // 각각의 div에서 span 태그를 추출함
                Elements spanHeadings = div.select("span.objHeading_h4.text-white");
                for (Element span : spanHeadings) {
                    String title = span.text();
                    titleList.add(title);
                }

                // div에서 tbody 태그 추출
                Elements tableElements = div.select("table");
                for (Element table : tableElements) {
                    Map<String, List<String>> parsedData = new LinkedHashMap<>();
                    String category = null;
                    List<String> tdList = new ArrayList<>();

                    // <th class="warning">인 경우 새로운 카테고리를 만듦
                    Elements trElements = table.select("tbody tr");
                    for (Element tr : trElements) {
                        Element th = tr.selectFirst("th.warning");
                        if (th != null) {
                            if (category != null) {
                                parsedData.put(category, tdList);
                            }

                            // 새로운 카테고리 설정
                            category = th.text();
                            tdList = new ArrayList<>();
                        }

                        // td values 추출 후 추가
                        Elements tdElements = tr.select("td");
                        for (Element td : tdElements) {
                            tdList.add(td.text());
                        }
                    }

                    if (category != null) {
                        parsedData.put(category, tdList);
                        mapList.add(parsedData);
                    }

                }
            }
        }

        return GradInfoConverter.toCompletionDto(mapList, titleList);
    }
}