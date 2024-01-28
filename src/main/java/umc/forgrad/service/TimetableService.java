package umc.forgrad.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

import java.io.IOException;
import java.util.*;

import static umc.forgrad.service.common.ConnectionResponse.getResponse;

@RequiredArgsConstructor
@Service
public class TimetableService {
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    @Transactional
    public List<AddTimetableRequestDto.HakkiDto> searchHakki(HttpSession session) throws IOException {
        String hakkiSearchUrl = "https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_aui.jsp";
        Connection.Response hakkisResponse = getResponse(session, hakkiSearchUrl);
        Document document = hakkisResponse.parse();

        List<AddTimetableRequestDto.HakkiDto> hakkiDtos = new ArrayList<>();
        Elements hakkis = document.select("#yearhakgi > option");
        for( Element element : hakkis ) {
            final String hakki = element.text();
            String[] parts = hakki.replace("년", "").replace("학기", "").split(" ");
            final Integer yearSemester = Integer.parseInt(parts[0] + parts[1]);
            AddTimetableRequestDto.HakkiDto hakkiDto = AddTimetableRequestDto.HakkiDto.builder()
                    .hakkiText(hakki)
                    .hakkiNum(yearSemester)
                    .build();
            hakkiDtos.add(hakkiDto);
        }
        return hakkiDtos;
    }
    @Transactional
    public List<AddTimetableRequestDto.TrackDto> searchTrack(HttpSession session) throws IOException {
        String trackSearchUrl = "https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_aui.jsp";
        Connection.Response tracksResponse = getResponse(session, trackSearchUrl);
        Document document = tracksResponse.parse();

        List<AddTimetableRequestDto.TrackDto> trackDtos = new ArrayList<>();
        Elements tracks = document.select("#jungong > option");
        for( Element element : tracks ) {
            final String text = element.text();
            String[] parts = text.split(" ");
            final String trackCode = parts[0];
            final String trackName = parts[1];
            AddTimetableRequestDto.TrackDto trackDto = AddTimetableRequestDto.TrackDto.builder()
                    .trackName(trackName)
                    .trackCode(trackCode)
                    .build();
            trackDtos.add(trackDto);
        }
        return trackDtos;
    }

    @Transactional
    public List<AddTimetableRequestDto.SearchSubjectDto> searchSubjects(HttpSession session, Integer hakki, String track) throws IOException {
        String subjectSearchUrl = String.format("https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_aui.jsp?gubun=history&syearhakgi=%d&sjungong=%s", hakki, track);
        Connection.Response subjectsResponse = getResponse(session, subjectSearchUrl);
        Document document = subjectsResponse.parse();

        List<AddTimetableRequestDto.SearchSubjectDto> searchSubjectDtos = new ArrayList<>();
        Elements grades = document.select("#grid_wrap > div > div.aui-grid-content-panel-mask > div > div.aui-grid-main-panel > div.aui-grid-body-panel > table > tbody > tr > td:nth-child(4) > div");
        Elements types = document.select("#grid_wrap > div > div.aui-grid-content-panel-mask > div > div.aui-grid-main-panel > div.aui-grid-body-panel > table > tbody > tr > td:nth-child(5) > div");
        Elements names = document.select("#grid_wrap > div > div.aui-grid-content-panel-mask > div > div.aui-grid-main-panel > div.aui-grid-body-panel > table > tbody > tr > td.aui-grid-default-column.text-start > div");
        Elements credits = document.select("#grid_wrap > div > div.aui-grid-content-panel-mask > div > div.aui-grid-main-panel > div.aui-grid-body-panel > table > tbody > tr > td:nth-child(6) > div");
        for( int i =0; i< names.size(); i++ ) {
            final Integer grade = Integer.parseInt(grades.get(i).text());
            final String type = types.get(i).text();
            final String name = names.get(i).text();
            final Integer credit = Integer.parseInt(credits.get(i).text());
            AddTimetableRequestDto.SearchSubjectDto searchSubjectDto = AddTimetableRequestDto.SearchSubjectDto.builder()
                    .searchGrade(grade)
                    .searchType(type)
                    .searchName(name)
                    .searchCredit(credit)
                    .build();
            searchSubjectDtos.add(searchSubjectDto);
        }
        return searchSubjectDtos;
    }
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
