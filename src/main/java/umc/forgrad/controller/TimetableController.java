package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.dto.Timetable.ViewTimetableResponseDto;
import umc.forgrad.service.TimetableService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @GetMapping(value = "/plans/timetable/searchHakki")
    public ApiResponse<List<AddTimetableRequestDto.HakkiDto>> searchHakki(HttpSession session) throws IOException {
        List<AddTimetableRequestDto.HakkiDto> hakkiDtos = timetableService.searchHakki(session);
        return ApiResponse.onSuccess(hakkiDtos);
    }
    @GetMapping(value = "/plans/timetable/searchTrack")
    public ApiResponse<List<AddTimetableRequestDto.TrackDto>> searchTrack(HttpSession session) throws IOException {
        List<AddTimetableRequestDto.TrackDto> trackDtos = timetableService.searchTrack(session);
        return ApiResponse.onSuccess(trackDtos);
    }
    @GetMapping(value = "/plans/timetable/searchSubject")
    public ApiResponse<List<AddTimetableRequestDto.SearchSubjectDto>> searchSubject(HttpSession session, Integer hakki, String track) throws IOException {
        List<AddTimetableRequestDto.SearchSubjectDto> searchSubjectDtos = timetableService.searchSubject(session, hakki, track);
        return ApiResponse.onSuccess(searchSubjectDtos);
    }

    @PostMapping(value = "/plans/timetable/searchTrack")
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
