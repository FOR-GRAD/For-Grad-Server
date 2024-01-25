package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.service.TimetableService;

@RestController
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @PostMapping(value = "/plans/timetable/stuId/{stuId}")
    public ApiResponse<AddTimetableResponseDto> addTimetable(@RequestBody AddTimetableRequestDto.TimetableDto timetableDto, @PathVariable Long stuId) {
        AddTimetableResponseDto addTimetableResponseDto = timetableService.addTimetable(timetableDto, stuId);
        return ApiResponse.onSuccess(addTimetableResponseDto);
    }
}
