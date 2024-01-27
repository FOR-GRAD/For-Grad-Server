package umc.forgrad.converter;

import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
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
                    .createdAt(LocalDateTime.now())
                    .build();
            addResponseDtos.add(addResponseDto);
        }
        return AddTimetableResponseDto.addResponseDtoList.builder()
                .addResponseDtos(addResponseDtos)
                .build();
    }
    public static Semester toSemester(AddTimetableRequestDto.SemesterDto semesterDto, Student student) {
        return Semester.builder()
                .grade(semesterDto.getGrade())
                .semester(semesterDto.getSemester())
                .student(student)
                .build();
    }
    public static Subject toSubject(AddTimetableRequestDto.SubjectDto subjectDto) {
        return Subject.builder()
                .type(subjectDto.getType())
                .name(subjectDto.getName())
                .credit(subjectDto.getCredit())
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
}
