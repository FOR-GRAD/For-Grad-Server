package umc.forgrad.service.student;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.StudentRepository;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    private final StudentRepository studentRepository;

    @Override
    public Student login(StudentRequestDto.LoginRequestDto loginRequestDto, HttpSession session) {

        long stuId = Long.parseLong(loginRequestDto.getId());

        // 종정시 로그인
        ResponseEntity<String> jjsResponse = getJjsResponse(loginRequestDto);
        Document jjsDocument = Jsoup.parse(Objects.requireNonNull(jjsResponse.getBody()));
        // redirectUrl 추출
        String redirectUrl = Objects.requireNonNull(jjsDocument.body().select("a").first()).attr("href");

        // 비교과 포인트 로그인
        ResponseEntity<String> hsportalResponse = getHsportalResponse(loginRequestDto);
        Document hsportalDocument = Jsoup.parse(Objects.requireNonNull(hsportalResponse.getBody()));

        // response의 "success" 값 추출
        Element body = hsportalDocument.body();
        String jsonString = body.getAllElements().text();
        JSONObject jsonObject = new JSONObject(jsonString);
        boolean success = jsonObject.getBoolean("success");

        Map<String, String> cookies = new HashMap<>();
        cookies.putAll(getCookies(jjsResponse));
        cookies.putAll(getCookies(hsportalResponse));
        session.setAttribute("cookies", cookies);

        if (redirectUrl.equals("http://info.hansung.ac.kr/h_dae/dae_main.html") && success) {
            return studentRepository.findById(stuId).orElse(studentRepository.save(StudentConverter.toStudent(stuId)));
        } else {
            throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);
        }

    }

    private static ResponseEntity<String> getJjsResponse(StudentRequestDto.LoginRequestDto loginRequestDto) {
        String url = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl"; // 종정시 로그인

        // Form Data 생성
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", loginRequestDto.getId());
        map.add("passwd", loginRequestDto.getPasswd());

        return httpResponse(map, url);
    }

    private static ResponseEntity<String> getHsportalResponse(StudentRequestDto.LoginRequestDto loginRequestDto) {
        String url = "https://hsportal.hansung.ac.kr/ko/process/member/login";  // 비교과 포인트 로그인

        // Form Data 생성
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", loginRequestDto.getId());
        map.add("password", loginRequestDto.getPasswd());

        return httpResponse(map, url);
    }

    private static ResponseEntity<String> httpResponse(MultiValueMap<String, String> map, String url) {
        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HttpEntity 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // http 요청하기
        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    private Map<String, String> getCookies(ResponseEntity<String> response) {
        // cookie 추출
        return Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).stream()
                .map(cookieStr -> {
                    String[] cookieArr = cookieStr.split(";\\s*");
                    String[] nameValue = cookieArr[0].split("=");
                    return new AbstractMap.SimpleEntry<>(nameValue[0], nameValue[1]);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    @Override
    public String logout(HttpSession session) throws IOException {

        // 세션 유효성 검사
        if (session == null || session.getAttribute("cookies") == null) {
            throw new GeneralException(ErrorStatus.SESSION_UNAUTHORIZED);
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
