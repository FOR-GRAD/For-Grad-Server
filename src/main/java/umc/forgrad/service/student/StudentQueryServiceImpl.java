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
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.Semester;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.StudentRepository;
import umc.forgrad.repository.SubjectRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentQueryServiceImpl implements StudentQueryService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public StudentResponseDto.HomeResponseDto queryHome(HttpSession session) throws IOException {
        // 이름, 학번, 학부, 학년, 재학상태 조회
        String gradesUrl = "https://info.hansung.ac.kr/jsp_21/student/grade/total_grade.jsp";

        Connection.Response gradesResponse = getResponse(session, gradesUrl);

        Document document = gradesResponse.parse();
        String text = Objects.requireNonNull(document.select("strong.objHeading_h3").first()).text(); // 황준현 (1871456) 컴퓨터공학부 4 학년 일반휴학
        // 괄호를 제거하고, 공백을 기준으로 문자열 분리
        String[] parts = text.replace("(", "").replace(")", "").split(" ");
        String name = parts[0];
        String id = parts[1];
        String department = parts[2];
        String grade = parts[3] + parts[4];
        String status = parts[5];

        log.info(name);
        log.info(id);
        log.info(department);
        log.info(grade);
        log.info(status);

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

        // 응원의 한마디 조회
        Optional<Student> optionalStudent = studentRepository.findById(Long.parseLong(id));
        Student student = optionalStudent.orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        String message = student.getMessage();

        // 시간표 조회
        Optional<Semester> optionalSemester = semesterRepository.findByStudentId(Long.parseLong(id));
        Semester semester = optionalSemester.orElseThrow(() -> new GeneralException(ErrorStatus.SEMESTER_NOT_FOUND));

//        subjectList.forEach(subject -> {
//            log.info(subject.getType());
//            log.info(subject.getName());
//            log.info(String.valueOf(subject.getCredit()));
//        });


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
