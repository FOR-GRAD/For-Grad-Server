package umc.forgrad.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.TestConverter;
import umc.forgrad.dto.TestResponse;

@RestController
public class TestController {

    @GetMapping("/test")
    public ApiResponse<TestResponse.TestDto> test() {
        return ApiResponse.onSuccess(TestConverter.toTestDto());
    }

}
