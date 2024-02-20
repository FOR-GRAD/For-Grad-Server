package umc.forgrad.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.Timetable.*;
import umc.forgrad.service.TimetableService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @GetMapping(value = "/plans/timetable/searchHakki")
    public ApiResponse<List<TimetableRequestDto.HakkiDto>> searchHakki(HttpSession session) throws IOException {
        List<TimetableRequestDto.HakkiDto> hakkiDtos = timetableService.searchHakki(session);
        return ApiResponse.onSuccess(hakkiDtos);
    }

    @GetMapping(value = "/plans/timetable/searchTrack")
    public ApiResponse<List<TimetableRequestDto.TrackDto>> searchTrack(HttpSession session, String hakki) throws IOException {
        List<TimetableRequestDto.TrackDto> trackDtos = timetableService.searchTrack(session, hakki);
        return ApiResponse.onSuccess(trackDtos);
    }

    @GetMapping(value = "/plans/timetable/searchSubject")
    public ApiResponse<List<TimetableRequestDto.SearchSubjectDto>> searchSubject(HttpSession session, String hakki, String track) throws IOException {
        List<TimetableRequestDto.SearchSubjectDto> searchSubjectDtos = timetableService.searchSubject(session, hakki, track);
        return ApiResponse.onSuccess(searchSubjectDtos);
    }

    @PostMapping(value = "/plans/timetable")
    public ApiResponse<AddTimetableResponseDto.addResponseDtoList> addTimetable(@RequestBody TimetableRequestDto.RealTimetableDto realTimetableDto, @SessionAttribute(name = "student") Long stuId) {
        AddTimetableResponseDto.addResponseDtoList addResponseDtoList = timetableService.addTimetable(realTimetableDto, stuId);
        return ApiResponse.onSuccess(addResponseDtoList);
    }

    @PutMapping(value = "/plans/timetable")
    public ApiResponse<UpdateTimetableResponseDto.updateResponseDtoList> updateTimetable(@RequestBody TimetableRequestDto.RealTimetableDto realTimetableDto, @SessionAttribute(name="student") Long stuId) {
        UpdateTimetableResponseDto.updateResponseDtoList updateResponseDtoList = timetableService.updateTimetable(realTimetableDto, stuId);
        return ApiResponse.onSuccess(updateResponseDtoList);
    }


    @GetMapping(value = "/plans/timetable")
    public ApiResponse<List<ViewTimetableResponseDto>> viewTimetable(@SessionAttribute(name="student") Long stuId, Integer grade, Integer semester) {
        List<ViewTimetableResponseDto> viewTimetableResponseDtos = timetableService.viewTimetable(stuId, grade, semester);
        return ApiResponse.onSuccess(viewTimetableResponseDtos);
    }

    @DeleteMapping(value = "/plans/timetable")
    public ApiResponse<List<ViewTimetableResponseDto>> deleteTimetable(@SessionAttribute(name = "student") Long stuId, Integer grade, Integer semester, Long subjectId) {
        timetableService.deleteTimetable(stuId, grade, semester, subjectId);
        return viewTimetable(stuId, grade ,semester);
    }
}
