package umc.forgrad.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.domain.Free;
import umc.forgrad.service.FreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ApiResponse<Free> create(Free free, @PathParam("stuId") String stuId) {
        // free =  {id = null, stuId = null, memo = "안녕하세요!!"}
        // 학생 아이디 부여
        free.setStuId(Long.parseLong(stuId));
        return ApiResponse.onSuccess(freeService.addMemo(free));
    }

    @GetMapping(value = "/plans/memo/stuId/{stuId}")
    public ApiResponse<Free> getMemo(@PathParam("stuId") String stuId) {

        // 학생 repo에서 메모ID를 알아내서 메모정보를 가져와야하나?

        Free memo = freeService.findMemos(Long.parseLong(stuId));

        return ApiResponse.onSuccess(memo);
    }
}