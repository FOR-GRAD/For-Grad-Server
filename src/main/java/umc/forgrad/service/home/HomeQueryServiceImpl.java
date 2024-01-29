package umc.forgrad.service.home;

import jakarta.servlet.http.HttpSession;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.FuturePlansCoverter;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.SemesterSubjectRepository;
import umc.forgrad.repository.StudentRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static umc.forgrad.service.common.ConnectionResponse.getResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeQueryServiceImpl implements HomeQueryService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    @Override
    public StudentResponseDto.HomeResponseDto queryHome(long studentId, HttpSession session) throws IOException {

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

        LocalDate date = LocalDate.now();
        int monthValue = date.getMonthValue(); // 현재 달
        int currentGrade = Integer.parseInt(parts[3]); // 현재 학년

        int[] nextGradeSemesterArr = getNextSemester(monthValue, currentGrade);
        int nextGrade = nextGradeSemesterArr[0];
        int nextSemester = nextGradeSemesterArr[1];

        // 종정시 사진 to base64Image
        String base64Image = getBase64Image(studentId, session);

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

        // 응원의 한마디 조회
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        String message = student.getMessage();

        // 시간표 조회
        // 학생의 학년과 학기로 해당 학기 찾기
        Optional<Semester> optionalSemester = semesterRepository.findByStudentAndGradeAndSemester(student, nextGrade, nextSemester);
        Semester semester = optionalSemester.orElseThrow(() -> new GeneralException(ErrorStatus.SEMESTER_NOT_FOUND));

        // 해당 학기에 속하는 과목 리스트 찾기
        List<SemesterSubject> semesterSubjectList = semesterSubjectRepository.findBySemester(semester);

        // list로 변경
        List<Subject> subjectList = semesterSubjectList.stream()
                .map(SemesterSubject::getSubject)
                .toList();

        // FutureTimeTableDto 변경
        Map<String, List<StudentResponseDto.FutureTimeTableDto>> futureTimeTableDto = FuturePlansCoverter.toFutureTimeTableDto(nextSemesterToString(nextGradeSemesterArr), subjectList);

        return StudentResponseDto.HomeResponseDto.builder()
                .name(name)
                .id(Integer.parseInt(id))
                .department(department)
                .grade(grade)
                .status(status)
                .message(message)
                .track1(track1)
                .track2(track2)
                .base64Image(base64Image)
                .trackRequirement1(trackRequirement1)
                .trackRequirement2(trackRequirement2)
                .note1(note1)
                .note2(note2)
                .futureTimeTableDto(futureTimeTableDto)
                .build();

    }

    // 다음학기 toString()
    private static String nextSemesterToString(int[] nextSemester) {
        return nextSemester[0] + "학년" + nextSemester[1] + "학기";
    }

    // 다음 학기 계산 로직
    public static int[] getNextSemester(int month, int grade) {
        int[] semester = new int[2];

        // 졸업 학년은 0학년으로 표시됨(4학년 2학기로 계산함)
        if (grade == 0) {
            semester[0] = 4;
            semester[1] = 2;
            return semester;
        }

        // 3월부터 9월까지는 현재 학년, 2학기를 저장
        if (month >= 3 && month <= 9) {
            semester[0] = grade;
            semester[1] = 2;
        }
        // 나머지 달에 대해서는 다음 학년 1학기를 저장
        else {
            // 4학년 2학기일 경우 마지막 학기이므로 4학년 2학기를 저장
            if (grade == 4) {
                semester[0] = 4;
                semester[1] = 2;
            } else {
                semester[0] = grade + 1;
                semester[1] = 1;
            }
        }
        return semester;
    }

    // 종정시 이미지 to Base64Image
    private static String getBase64Image(long studentId, HttpSession session) {
        String imgUrl = "https://info.hansung.ac.kr/tonicsoft/jik/register/haksang_sajin.jsp?hakbun=" + studentId;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");
        StringBuilder sb = new StringBuilder();
        for (String key : cookies.keySet()) {
            sb.append(key).append("=").append(cookies.get(key)).append(";");
        }
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));
        headers.add("Cookie", String.valueOf(sb));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(imgUrl, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] imageBytes = response.getBody();

            return DatatypeConverter.printBase64Binary(imageBytes);
        }
        return null;
    }

}
