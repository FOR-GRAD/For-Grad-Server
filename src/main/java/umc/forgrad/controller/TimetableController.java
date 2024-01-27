package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.dto.Timetable.ViewTimetableResponseDto;
import umc.forgrad.service.TimetableService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @PostMapping(value = "/plans/timetable")
    public ApiResponse<AddTimetableResponseDto.addResponseDtoList> addTimetable(@RequestBody AddTimetableRequestDto.TimetableDto timetableDto, @SessionAttribute(name="student") Long stuId) {
        AddTimetableResponseDto.addResponseDtoList addResponseDtoList = timetableService.addTimetable(timetableDto, stuId);
        return ApiResponse.onSuccess(addResponseDtoList);
    }

    @GetMapping(value = "/plans/timetable")
    public ApiResponse<List<ViewTimetableResponseDto>> viewTimetable(@SessionAttribute(name="student") Long stuId, Integer grade, Integer semester) {
        List<ViewTimetableResponseDto> viewTimetableResponseDtos = timetableService.viewTimetable(stuId, grade, semester);
        return ApiResponse.onSuccess(viewTimetableResponseDtos);
    }
}
