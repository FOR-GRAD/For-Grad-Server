package umc.forgrad.service.student;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.exception.GeneralException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    private Map<String, String> cookieMap = new HashMap<>();

    @Override
    public String login(StudentRequestDto.LoginRequestDto loginRequestDto) throws IOException {

        String url = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";

        Connection.Response response = Jsoup.connect(url)
                .data("id", loginRequestDto.getId(), "passwd", loginRequestDto.getPasswd())
                .method(Connection.Method.POST)
                .execute();

        // 쿠키 저장
        cookieMap = response.cookies();

        if (response.hasCookie("ssotoken")) {
            return "login success";
        } else {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

    }

}
