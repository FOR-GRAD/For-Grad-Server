package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.service.NoticeService;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    @GetMapping("/departmentUrl/{trackNum}")
    public ApiResponse<String> getUrl(@PathVariable int trackNum, @SessionAttribute(name = "student") Long studentId){
        String link = noticeService.getLink(trackNum,studentId);
              return ApiResponse.onSuccess(link);

    }

}
