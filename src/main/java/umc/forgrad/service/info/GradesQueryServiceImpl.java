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
import umc.forgrad.service.common.ConnectionResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GradesQueryServiceImpl implements GradesQueryService {

    @Override
    public GradInfoResponseDto.MyGradesInfoDto queryGrades(HttpSession session) throws IOException {

        // 학기별 정보 저장
        Map<String, GradInfoResponseDto.GradesListDtoAndTotalDto> myGradesInfoListDto = new LinkedHashMap<>();

        String gradesUrl = "https://info.hansung.ac.kr/fuz/seongjeok/seongjeok.jsp";
        Connection.Response gradesResponse = ConnectionResponse.getResponse(session, gradesUrl);

        Document gradesDocument = gradesResponse.parse();

        Elements center = gradesDocument.select("body > font > center");
        for (Element rows : center) {
            Elements tables = rows.children();

            int totalTableSize = tables.size() - 5; // -5는 <br>태그와 마지막 총 성적 내역 제거

            // 2018 학년도 1 학기, [신청학점, 취득학점, 평점총계, 평균평점, 백분위점수] 행 추출
            for (int i = 0, j = 1; i < totalTableSize; i += 2, j += 2) {
                // 각 학기별 새로운 Map과 List 생성
                List<GradInfoResponseDto.GradesDto> gradesDtoList = new ArrayList<>();

                Element oddTable = tables.get(i);

                // 학기 정보 추출
                String semester = oddTable.select("td[align=left]").text();

                // 합계 정보 추출
                Elements totalElements = oddTable.select("td[align=center]");

                String appliedCredits = totalElements.get(0).text();
                String acquiredCredits = totalElements.get(1).text();
                String totalGrade = totalElements.get(2).text();
                String averageGrade = totalElements.get(3).text();
                String percentile = totalElements.get(4).text();

                GradInfoResponseDto.GradesTotalDto gradesTotalDto = GradInfoResponseDto.GradesTotalDto.builder()
                        .appliedCredits(appliedCredits)
                        .acquiredCredits(acquiredCredits)
                        .totalGrade(totalGrade)
                        .averageGrade(averageGrade)
                        .percentile(percentile)
                        .build();


                // [교과명, 이수구분, 학점, 성적] 행 추출
                Element evenTable = tables.get(j);

                Elements tr = evenTable.select("tr");
                for (int k = 1; k < tr.size(); k++) {
                    Elements td = tr.get(k).children();

                    String classification = td.get(0).text();
                    String subjectName = td.get(2).text();
                    String credits = td.get(3).text();
                    String grade = td.get(4).text();

                    gradesDtoList.add(
                            GradInfoResponseDto.GradesDto.builder()
                                    .classification(classification)
                                    .subjectName(subjectName)
                                    .credits(credits)
                                    .grade(grade)
                                    .build()
                    );

                }

                // 최종 리턴 dto 생성
                GradInfoResponseDto.GradesListDtoAndTotalDto gradesListDtoAndTotalDto = GradInfoResponseDto.GradesListDtoAndTotalDto.builder()
                        .gradesDtoList(gradesDtoList)
                        .gradesTotalDto(gradesTotalDto)
                        .build();

                // map에 dto 추가
                myGradesInfoListDto.put(semester, gradesListDtoAndTotalDto);
            }


        }

        return GradInfoConverter.toMyGradesListMapDto(myGradesInfoListDto);

    }

}
