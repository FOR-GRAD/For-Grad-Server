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

    @PostMapping(value = "/plans/timetable/stuId")
    public ApiResponse<AddTimetableResponseDto> addTimetable(@RequestBody AddTimetableRequestDto.TimetableDto timetableDto, @SessionAttribute(name="student") Long stuId) {
        AddTimetableResponseDto addTimetableResponseDto = timetableService.addTimetable(timetableDto, stuId);
        return ApiResponse.onSuccess(addTimetableResponseDto);
    }

    @GetMapping(value = "/plans/timetable/stuId")
    public ApiResponse<List<ViewTimetableResponseDto>> viewTimetable(@SessionAttribute(name="student") Long stuId, Integer grade, Integer semester) {
        List<ViewTimetableResponseDto> viewTimetableResponseDtos = timetableService.viewTimetable(stuId, grade, semester);
        return ApiResponse.onSuccess(viewTimetableResponseDtos);
    }
}
