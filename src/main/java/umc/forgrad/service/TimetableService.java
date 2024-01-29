package umc.forgrad.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.converter.TimetableConverter;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;
import umc.forgrad.dto.Timetable.*;
import umc.forgrad.repository.SemesterRepository;
import umc.forgrad.repository.SemesterSubjectRepository;
import umc.forgrad.repository.StudentRepository;
import umc.forgrad.repository.SubjectRepository;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TimetableService {
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    @Transactional
    public List<TimetableRequestDto.HakkiDto> searchHakki(HttpSession session) throws IOException {
        String hakkiSearchUrl = "https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_data_aui.jsp";

        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        Connection.Response execute = Jsoup.connect(hakkiSearchUrl)
                .cookies(cookies)
                .data("gubun", "yearhakgilist")
                .method(Connection.Method.POST)
                .execute();

        Document doc = execute.parse();
        Elements items = doc.select("item");
        List<TimetableRequestDto.HakkiDto> hakkiDtos = new ArrayList<>();
        for (Element item : items) {
            final String hakkiNum = item.select("tcd").text();
            final String hakkiText = item.select("tnm").text();
            TimetableRequestDto.HakkiDto hakkiDto = TimetableRequestDto.HakkiDto.builder()
                    .hakkiNum(hakkiNum)
                    .hakkiText(hakkiText)
                    .build();
            hakkiDtos.add(hakkiDto);
        }
        return hakkiDtos;
    }
    @Transactional
    public List<TimetableRequestDto.TrackDto> searchTrack(HttpSession session, String hakki) throws IOException {
        String trackSearchUrl = "https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_data_aui.jsp";

        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        Connection.Response execute = Jsoup.connect(trackSearchUrl)
                .cookies(cookies)
                .data("gubun", "jungonglist", "syearhakgi", hakki)
                .method(Connection.Method.POST)
                .execute();

        Document doc = execute.parse();
        Elements items = doc.select("item");
        List<TimetableRequestDto.TrackDto> trackDtos = new ArrayList<>();
        for (Element item : items) {
            final String trackCode = item.select("tcd").text();
            final String trackName = item.select("tnm").text();
            TimetableRequestDto.TrackDto trackDto = TimetableRequestDto.TrackDto.builder()
                    .trackCode(trackCode)
                    .trackName(trackName)
                    .build();
            trackDtos.add(trackDto);
        }
        return trackDtos;
    }

    public List<TimetableRequestDto.SearchSubjectDto> searchSubject(HttpSession session, String hakki, String track) throws IOException {
        String subjectSearchUrl = "https://info.hansung.ac.kr/jsp_21/student/kyomu/kyoyukgwajung_data_aui.jsp";

        Map<String, String> cookies = (Map<String, String>) session.getAttribute("cookies");

        Connection.Response execute = Jsoup.connect(subjectSearchUrl)
                .cookies(cookies)
                .data("gubun", "history", "syearhakgi", hakki, "sjungong", track)
                .method(Connection.Method.POST)
                .execute();

        Document doc = execute.parse();
        Elements rows = doc.select("row");
        List<TimetableRequestDto.SearchSubjectDto> searchSubjectDtos = new ArrayList<>();
        for (Element row : rows) {
            final String grade = row.select("haknean").text();
            final String type = row.select("isugubun").text();
            final String name = row.select("kwamokname").text();
            final String credit = row.select("hakjum").text();
            TimetableRequestDto.SearchSubjectDto searchSubjectDto = TimetableRequestDto.SearchSubjectDto.builder()
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
    public AddTimetableResponseDto.addResponseDtoList addTimetable(TimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));

        Semester semester = semesterRepository.save(TimetableConverter.toSemester(timetableDto.getSemesterDto(), student));
        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();
        List<SemesterSubject> semesterSubjects = new ArrayList<>();
        for(TimetableRequestDto.SubjectDto dto : subjectDtoList) {
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
    @Transactional
    public UpdateTimetableResponseDto updateTimetable(TimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        Semester semester = TimetableConverter.toSemester(timetableDto.getSemesterDto(), student);
        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();
        for(TimetableRequestDto.SubjectDto dto: subjectDtoList) {
            Subject subject = subjectRepository.findByIdAndSemester(,semester);

        }
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
