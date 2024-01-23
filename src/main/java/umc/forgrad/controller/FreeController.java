package umc.forgrad.controller;

import org.springframework.web.bind.annotation.PathVariable;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.domain.Free;
import umc.forgrad.service.FreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class FreeController {

    private final FreeService freeService;

    @Autowired
    public FreeController(FreeService freeService) {
        this.freeService = freeService;
    }

    @PostMapping(value = "/plans/memo/stuId/{stuId}")
    public ApiResponse<Free> create(Free free, @PathVariable("stuId") String stuId) {
        // free =  {id = null, stuId = null, memo = "안녕하세요!!"}

        free.setStuid(Long.parseLong(stuId)); // 학생 아이디 부여
        return ApiResponse.onSuccess(freeService.addMemo(free));
    }

    @GetMapping(value = "/plans/memo/stuId/{stuId}")
    public ApiResponse<Free> getMemo(@PathVariable("stuId") String stuId) {

        Free memo = freeService.findMemos(Long.parseLong(stuId));

        return ApiResponse.onSuccess(memo);
    }
}