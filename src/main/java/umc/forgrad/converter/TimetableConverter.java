package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.Timetable;
import umc.forgrad.dto.Timetable.TimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.dto.Timetable.UpdateTimetableResponseDto;
import umc.forgrad.dto.Timetable.ViewTimetableResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableConverter {
    public static AddTimetableResponseDto.addResponseDtoList toAddResultDto(List<Subject> subjects) {
        List<AddTimetableResponseDto.addResponseDto> addResponseDtos = new ArrayList<>();
        for(Subject subject : subjects) {
            AddTimetableResponseDto.addResponseDto addResponseDto = AddTimetableResponseDto.addResponseDto.builder()
                    .subjectId(subject.getId())
                    .createdAt(subject.getCreatedAt())
                    .build();
            addResponseDtos.add(addResponseDto);
        }
        return AddTimetableResponseDto.addResponseDtoList.builder()
                .addResponseDtos(addResponseDtos)
                .build();
    }
    public static Timetable toSemester(TimetableRequestDto.SemesterDto semesterDto, Student student) {
        return Timetable.builder()
                .grade(semesterDto.getGrade())
                .semester(semesterDto.getSemester())
                .student(student)
                .build();
    }
    public static Subject toSubject(TimetableRequestDto.SubjectDto subjectDto, Timetable timetable) {
        return Subject.builder()
                .type(subjectDto.getType())
                .name(subjectDto.getName())
                .credit(subjectDto.getCredit())
                .timetable(timetable)
                .build();
    }
    public static List<ViewTimetableResponseDto> toViewResultDto(List<Subject> subjects) {
        return subjects.stream()
                .map(subject -> ViewTimetableResponseDto.builder()
                        .subjectId(subject.getId())
                        .type(subject.getType())
                        .name(subject.getName())
                        .credit(subject.getCredit())
                        .build())
                .collect(Collectors.toList());
    }

    public static UpdateTimetableResponseDto.updateResponseDtoList toUpdateResultDto(List<Subject> subjects) {
        List<UpdateTimetableResponseDto.updateResponseDto> updateResponseDtos = new ArrayList<>();
        for(Subject subject : subjects) {
            UpdateTimetableResponseDto.updateResponseDto updateResponseDto = UpdateTimetableResponseDto.updateResponseDto.builder()
                    .subjectId(subject.getId())
                    .updatedAt(subject.getUpdatedAt())
                    .build();
            updateResponseDtos.add(updateResponseDto);
        }
        return UpdateTimetableResponseDto.updateResponseDtoList.builder()
                .updateResponseDtos(updateResponseDtos)
                .build();
    }
}
