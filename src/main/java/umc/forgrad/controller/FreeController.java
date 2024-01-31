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

    @PostMapping("/plans/memo")
    public ApiResponse<FreeDto.MemoResponseDto> create(@RequestBody FreeDto.MemoRequestDto freeDto, @SessionAttribute(name="student") long stuId) {

        return ApiResponse.onSuccess(freeService.addMemo(freeDto, stuId));
    }

    @GetMapping("/plans/memo")
    public ApiResponse<FreeDto.MemoResponseDto> getMemo(@SessionAttribute(name="student") long stuId) {

        FreeDto.MemoResponseDto responseDto = freeService.findMemos(stuId);

        return ApiResponse.onSuccess(responseDto);
    }

    @PatchMapping("/plans/memo")
    public ApiResponse<FreeDto.MemoResponseDto> update(@RequestBody FreeDto.MemoRequestDto freeDto, @SessionAttribute(name="student") long stuId) {

        return ApiResponse.onSuccess(freeService.updateMemo(freeDto, stuId));
    }
}