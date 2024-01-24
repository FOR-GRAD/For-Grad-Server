package umc.forgrad.service.info;

import jakarta.servlet.http.HttpSession;
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
public class CompletionQueryServiceImpl implements CompletionQueryService {

    @Override
    public GradInfoResponseDto.CompletionStatusDto getCompletionStatus(HttpSession session) throws IOException {

        String gradesUrl = "https://info.hansung.ac.kr/jsp_21/student/grade/total_grade.jsp";
        Connection.Response gradesResponse = getResponse(session, gradesUrl);

        Document gradesDocument = gradesResponse.parse();

        // 교양 - 이수한 학점
        Elements generalTable = gradesDocument.select("#div_print_area > div > div._obj._objHtml._absolute > div > div.container > div.row > div > div.card-body.div_notice_content > div > div.col-12.col-lg-6 > div > div.card-body > div > table > tbody");
        Elements generalRows = generalTable.select("tr");

        Map<String, Map<String, String>> generalMap = new LinkedHashMap<>();
        String currentCategory = "";

        for (Element row : generalRows) {
            Elements ths = row.select("th");
            Elements tds = row.select("td");

            if (!ths.isEmpty()) {
                currentCategory = Objects.requireNonNull(ths.first()).text();
                generalMap.putIfAbsent(currentCategory, new LinkedHashMap<>());
            }

            if (tds.size() == 1) {
                String totalCredits = tds.get(0).text();
                generalMap.get(currentCategory).put(currentCategory, totalCredits);
            }

            if (tds.size() >= 2) {
                String subject = tds.get(0).text();
                String credit = tds.get(1).text();
                generalMap.get(currentCategory).put(subject, credit);
            }

            if (tds.size() == 4) {
                String subject = tds.get(2).text();
                String credit = tds.get(3).text();
                generalMap.get(currentCategory).put(subject, credit);
            }
        }

        GradInfoResponseDto.GeneralCompletionDto generalCompletionDto = GradInfoResponseDto.GeneralCompletionDto.builder()
                .generalMap(generalMap)
                .build();

        // 전공 - 이수한 학점
        Elements majorTable = gradesDocument.select("#div_print_area > div > div._obj._objHtml._absolute > div > div.container > div.row > div > div.card-body.div_notice_content > div > div.col-12.col-md-6 > div > div.card-body > div > table:nth-child(1)");
        Elements majorRows = majorTable.select("tr");

        Map<String, List<String>> majorMap = new LinkedHashMap<>();

        for (Element row : majorRows) {
            Elements ths = row.select("th");
            Elements tds = row.select("td");

            if (!ths.isEmpty() && !tds.isEmpty()) {
                String key = Objects.requireNonNull(ths.first()).text();
                List<String> values = new ArrayList<>();

                for (int i = 0; i < tds.size(); i++) {
                    Element td = tds.get(i);
                    if (i == tds.size() - 1 && tds.size() == 4) {
                        majorMap.put("총합계", Collections.singletonList(td.text()));
                    } else {
                        values.add(td.text());
                    }
                }

                majorMap.put(key, values);
            }
        }

        GradInfoResponseDto.MajorCompletionDto majorCompletionDto = GradInfoResponseDto.MajorCompletionDto.builder()
                .majorMap(majorMap)
                .build();

        return GradInfoConverter.toCompletionDto(generalCompletionDto, majorCompletionDto);

    }

}
