package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.StudentRepository;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    private final StudentRepository studentRepository;

    @Override
    public String login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException {

        log.info(session.getId());

        String url = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";

        Connection.Response response = Jsoup.connect(url)
                .data("id", loginRequestDto.getId(), "passwd", loginRequestDto.getPasswd())
                .method(Connection.Method.POST)
                .execute();

        // 쿠키 저장
        session.setAttribute("cookies", response.cookies());

        if (response.hasCookie("TS016c2283")) {
            Student student = StudentConverter.toStudent(Long.parseLong(loginRequestDto.getId()));
            studentRepository.save(student);
            return "login success";
        } else {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

    }

    @Override
    public String logout(HttpSession session) throws IOException {

        String url = "https://info.hansung.ac.kr/sso_logout.jsp";

        @SuppressWarnings(value = "unchecked")
        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        Jsoup.connect(url)
                .cookies(cookies)
                .method(Connection.Method.GET)
                .execute();

        // 세션 삭제
        session.invalidate();

        return "logout success";
    }
}
