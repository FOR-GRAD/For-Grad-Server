package umc.forgrad.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.converter.TimetableConverter;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.SemesterSubjectRepository;
import umc.forgrad.repository.StudentRepository;
import umc.forgrad.repository.SubjectRepository;

@RequiredArgsConstructor
@Service
public class TimetableService {
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    @Transactional
    public AddTimetableResponseDto addTimetable(AddTimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        Semester semester = semesterRepository.save(TimetableConverter.toSemester(timetableDto.getSemesterDto(), student));
        Subject subject = subjectRepository.save(TimetableConverter.toSubject(timetableDto.getSubjectDto()));
        SemesterSubject semesterSubject = SemesterSubject.builder()
                .subject(subject)
                .semester(semester)
                .build();
        semesterSubjectRepository.save(semesterSubject);
        return TimetableConverter.toAddResultDto(semester, subject);
    }
}
