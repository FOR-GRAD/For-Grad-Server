package umc.forgrad.controller;

import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.FreeDto;
import umc.forgrad.service.FreeService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class FreeController {

    private final FreeService freeService;

    @Autowired
    public FreeController(FreeService freeService) {
        this.freeService = freeService;
    }

    @PostMapping("/plans/memo/stuId/{stuId}")
    public ApiResponse<FreeDto.MemoResponseDto> create(@RequestBody FreeDto.MemoRequestDto freeDto, @PathVariable("stuId") long stuId) {
        // free =  {id = null, stuId = null, memo = "안녕하세요!!"}

//        free.setStuid(Long.parseLong(stuId)); // 학생 아이디 부여
        return ApiResponse.onSuccess(freeService.addMemo(freeDto, stuId));
    }

    @GetMapping("/plans/memo/stuId/{stuId}")
    public ApiResponse<FreeDto.MemoResponseDto> getMemo(@PathVariable("stuId") long stuId) {

        FreeDto.MemoResponseDto responseDto = freeService.findMemos(stuId);

        return ApiResponse.onSuccess(responseDto);
    }
}