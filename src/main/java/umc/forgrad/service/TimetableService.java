package umc.forgrad.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.FuturePlansCoverter;
import umc.forgrad.converter.TimetableConverter;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;
import umc.forgrad.dto.Timetable.AddTimetableRequestDto;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.dto.Timetable.ViewTimetableResponseDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.SemesterSubjectRepository;
import umc.forgrad.repository.StudentRepository;
import umc.forgrad.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TimetableService {
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    @Transactional
    public AddTimetableResponseDto.addResponseDtoList addTimetable(AddTimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));

        Semester semester = semesterRepository.save(TimetableConverter.toSemester(timetableDto.getSemesterDto(), student));

        List<AddTimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();
        List<SemesterSubject> semesterSubjects = new ArrayList<>();
        for(AddTimetableRequestDto.SubjectDto dto : subjectDtoList) {
            Subject subject = TimetableConverter.toSubject(dto);
            subjects.add(subject);
            SemesterSubject semesterSubject = SemesterSubject.builder()
                    .semester(semester)
                    .subject(subject)
                    .build();
            semesterSubjects.add(semesterSubject);
        }
        subjectRepository.saveAll(subjects);
        semesterSubjectRepository.saveAll(semesterSubjects);
        return TimetableConverter.toAddResultDto(subjects);
    }

    @Transactional(readOnly = true)
    public List<ViewTimetableResponseDto> viewTimetable(Long stuId, Integer grade, Integer semester) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        List<Semester> semesters = semesterRepository.findAllByStudentAndGradeAndSemester(student, grade, semester);
        List<SemesterSubject> semesterSubjects = new ArrayList<>();
        for (Semester semesterE : semesters) {
            List<SemesterSubject> semesterSubject = semesterSubjectRepository.findBySemester(semesterE);
            semesterSubjects.addAll(semesterSubject);
        }
        List<Subject> subjects = semesterSubjects.stream()
                .map(SemesterSubject::getSubject)
                .toList();

        return TimetableConverter.toViewResultDto(subjects);
    }
}
