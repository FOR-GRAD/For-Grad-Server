package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.dto.AddTimetableRequestDto;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.SemesterSubjectRepository;
import umc.forgrad.repository.SubjectRepository;

@RestController
@RequiredArgsConstructor
public class TimetableController {
    private final SemesterRepository semesterRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;
    private final SubjectRepository subjectRepository;

    @PostMapping(value = "/plans/timetable/stuId/{stuId}")
    public ApiResponse<AddTimetableRequestDto> addTimetable(@RequestBody AddTimetableRequestDto addTimetableRequestDto, @PathVariable Long stuId) {

    }
}
