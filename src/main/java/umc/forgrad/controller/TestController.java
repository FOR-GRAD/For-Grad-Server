package umc.forgrad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.TestConverter;
import umc.forgrad.dto.TestResponse;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public ApiResponse<TestResponse.TestDto> test() {
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
        return ApiResponse.onSuccess(TestConverter.toTestDto());
    }

}
