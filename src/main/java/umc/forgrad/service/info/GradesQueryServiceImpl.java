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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GradesQueryServiceImpl implements GradesQueryService {

    @Override
    public GradInfoResponseDto.MyGradesInfoDto queryGrades(HttpSession session) throws IOException {

        // 학기별 정보 저장
        Map<String, GradInfoResponseDto.GradesListDtoAndTotalDto> myGradesInfoListDto = new HashMap<>();

        String gradesUrl = "https://info.hansung.ac.kr/jsp_21/student/grade/total_grade.jsp";
        Connection.Response gradesResponse = ConnectionResponse.getResponse(session, gradesUrl);

        Document gradesDocument = gradesResponse.parse();

        // 학기별 요소들을 순회하며 정보 추출
        Elements semesters = gradesDocument.select("#div_print_area > div > div._obj._objHtml._absolute > div > div.row").select("div.col-12.col-lg-6.col-print-6");
        for (Element semester : semesters) {

            // 각 학기별 새로운 Map과 List 생성
            List<GradInfoResponseDto.GradesDto> gradesDtoList = new ArrayList<>();

            String semesterName = semester.select("div.card-header.bgh-blue span.objHeading_h3").text(); // 학기
            String appliedCredits = semester.select("div.div_sub_subdiv.card.card-info:contains(신청학점) div.card-body").text(); // 신청학점
            String acquiredCredits = semester.select("div.div_sub_subdiv.card.card-info:contains(취득학점) div.card-body").text(); // 취득학점
            String totalGrade = semester.select("div.div_sub_subdiv.card.card-info:contains(평점총계) div.card-body").text(); // 평점총계
            String averageGrade = semester.select("div.div_sub_subdiv.card.card-info:contains(평균평점) div.card-body").text(); // 평균평점
            String percentile = semester.select("div.div_sub_subdiv.card.card-info:contains(백분위) div.card-body").text(); // 백분위

            GradInfoResponseDto.GradesTotalDto gradesTotalDto = GradInfoResponseDto.GradesTotalDto.builder()
                    .appliedCredits(appliedCredits)
                    .acquiredCredits(acquiredCredits)
                    .totalGrade(totalGrade)
                    .averageGrade(averageGrade)
                    .percentile(percentile)
                    .build();


            Elements subjects = semester.select("table.table_1 tbody tr");
            for (Element subject : subjects) {

                String classification = subject.select("td").get(0).text(); // 구분(선필교, 전필, 일선, ...)
                String subjectName = subject.select("td.text-start").text(); // 교과명
                String credits = subject.select("td").get(3).text(); // 학점
                String grade = subject.select("td").get(4).text(); // 성적
                String track = subject.select("td").get(5).text(); // 현재트랙

                gradesDtoList.add(
                        GradInfoResponseDto.GradesDto.builder()
                                .classification(classification)
                                .subjectName(subjectName)
                                .credits(credits)
                                .grade(grade)
                                .track(track)
                                .build()
                );

            }

            GradInfoResponseDto.GradesListDtoAndTotalDto gradesListDtoAndTotalDto = GradInfoResponseDto.GradesListDtoAndTotalDto.builder()
                    .gradesDtoList(gradesDtoList)
                    .gradesTotalDto(gradesTotalDto)
                    .build();

            myGradesInfoListDto.put(semesterName, gradesListDtoAndTotalDto);

        }

        return GradInfoConverter.toMyGradesListMapDto(myGradesInfoListDto);

    }

}
