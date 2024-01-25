package umc.forgrad.converter;

import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;

import java.time.LocalDateTime;

public class TimetableConverter {
    public static AddTimetableResponseDto toAddResultDto(Semester semester, Subject subject) {
        return AddTimetableResponseDto.builder()
                .grade(semester.getGrade())
                .semester(semester.getSemester())
                .subjectId(subject.getId())
                .createdAt(LocalDateTime.now())
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
}
