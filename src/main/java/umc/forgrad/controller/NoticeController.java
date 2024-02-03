package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.service.NoticeService;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    @GetMapping("/departmentUrl/{department}")
    public ApiResponse<String> getUrl(@PathVariable String department){
        String link = noticeService.getLink(department);
        return ApiResponse.onSuccess(link);
    }

}
