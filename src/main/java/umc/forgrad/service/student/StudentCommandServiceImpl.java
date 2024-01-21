package umc.forgrad.service.student;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.StudentConverter;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.exception.GeneralException;

@Service
@Slf4j
public class StudentCommandServiceImpl implements StudentCommandService {

    @Override
    public String login(StudentRequestDto.LoginRequestDto loginRequestDto) {
        ChromeOptions options = getChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        StudentRequestDto.LoginRequestDto loginForm = StudentConverter.toLoginForm(loginRequestDto);

        // 로그인 페이지 열기
        driver.get("https://info.hansung.ac.kr/index.jsp");

        // 아이디와 비밀번호 입력 요소 찾기
        WebElement id = driver.findElement(By.id("id")); // 실제 아이디 입력 요소의 id로 변경해주세요.
        WebElement password = driver.findElement(By.id("passwd")); // 실제 비밀번호 입력 요소의 id로 변경해주세요.

        // 아이디와 비밀번호 입력
        id.sendKeys(loginForm.getId()); // 실제 아이디로 변경해주세요.
        password.sendKeys(loginForm.getPasswd()); // 실제 비밀번호로 변경해주세요.

        // 로그인 버튼 클릭
        WebElement loginButton = driver.findElement(By.id("loginBtn")); // 실제 로그인 버튼의 id로 변경해주세요.
        loginButton.click();

        try {
            driver.findElement(By.xpath("//*[@id=\"loginNotice\"]/p"));
        } catch (Exception e) {
            return "login success";
        }

        throw new GeneralException(ErrorStatus.LOGIN_UNAUTHORIZED);

    }

    private static ChromeOptions getChromeOptions() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver-linux64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        options.addArguments("lang=ko_KR");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return options;
    }

}
