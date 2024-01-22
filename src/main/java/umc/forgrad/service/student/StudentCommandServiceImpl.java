package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.exception.GeneralException;

import java.io.IOException;

@Service
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    @Override
    public String login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) throws IOException {

        String url = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";

        Connection.Response response = Jsoup.connect(url)
                .data("id", loginRequestDto.getId(), "passwd", loginRequestDto.getPasswd())
                .method(Connection.Method.POST)
                .execute();

        // 쿠키 저장
        session.setAttribute("cookies", response.cookies());

        if (response.hasCookie("ssotoken")) {
            return "login success";
        } else {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

    }

}
