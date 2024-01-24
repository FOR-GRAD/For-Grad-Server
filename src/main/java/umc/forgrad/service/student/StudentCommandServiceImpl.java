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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    private final StudentRepository studentRepository;

    @Override
    public Student login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException {

        String url = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";

        Connection.Response response = Jsoup.connect(url)
                .data("id", loginRequestDto.getId(), "passwd", loginRequestDto.getPasswd())
                .method(Connection.Method.POST)
                .execute();

        // 쿠키 저장
        session.setAttribute("cookies", response.cookies());

        if (response.hasCookie("ssotoken")) {
            Student student = StudentConverter.toStudent(Long.parseLong(loginRequestDto.getId()));
            studentRepository.save(student);
            return student;
        } else {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

    }

    @Override
    public String logout(HttpSession session) throws IOException {

        // 세션 유효성 검사
        if (session == null || session.getAttribute("cookies") == null) {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

        String url = "https://info.hansung.ac.kr/sso_logout.jsp";

        @SuppressWarnings(value = "unchecked")
        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        Jsoup.connect(url)
                .cookies(Objects.requireNonNull(cookies))
                .method(Connection.Method.GET)
                .execute();

        // 세션 삭제
        session.invalidate();
        return "logout success";
    }

}
