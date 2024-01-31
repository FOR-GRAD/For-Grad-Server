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
import umc.forgrad.dto.Timetable.*;
import umc.forgrad.repository.SemesterRepository;
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

    @Transactional
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
            final Integer credit =  Integer.parseInt(row.select("hakjum").text());
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

        Semester semester = TimetableConverter.toSemester(timetableDto.getSemesterDto(), student);
        Optional<Semester> existingSemester = semesterRepository.findByStudentAndGradeAndSemester(student, timetableDto.getSemesterDto().getGrade(), timetableDto.getSemesterDto().getSemester());
        if (existingSemester.isPresent()) {
            semester = existingSemester.get();
        } else {
            semester = semesterRepository.save(semester);
        }

        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();

        for (TimetableRequestDto.SubjectDto dto : subjectDtoList) {
            Subject subject = TimetableConverter.toSubject(dto, semester);
            subjects.add(subject);
        }
        subjectRepository.saveAll(subjects);
        return TimetableConverter.toAddResultDto(subjects);
    }
    @Transactional
    public UpdateTimetableResponseDto.updateResponseDtoList updateTimetable(TimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));

        Optional<Semester> optionalSemester = semesterRepository.findByStudentAndGradeAndSemester(student, timetableDto.getSemesterDto().getGrade(), timetableDto.getSemesterDto().getSemester());
        Semester semester = optionalSemester.get();
        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();
        for(TimetableRequestDto.SubjectDto dto: subjectDtoList) {
            Subject subject = subjectRepository.findByIdAndSemester_id(dto.getSubjuctId(), semester.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));
            subject.update(dto.getType(), dto.getName(), dto.getCredit());
            subjects.add(subject);
        }
        return TimetableConverter.toUpdateResultDto(subjects);
    }

    @Transactional(readOnly = true)
    public List<ViewTimetableResponseDto> viewTimetable(Long stuId, Integer grade, Integer semester) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        Semester semesterE = semesterRepository.findByStudentAndGradeAndSemester(student, grade, semester)
                .orElseThrow(() -> new IllegalArgumentException("해당 학년 학기가 존재하지 않습니다."));
        List<Subject> subjects = semesterE.getSubjectList();
        return TimetableConverter.toViewResultDto(subjects);
    }


    @Transactional
    public UpdateTimetableResponseDto.updateResponseDtoList deleteTimetable(long subjectId, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));

        Semester semester = semesterRepository.findById(1L).orElseThrow();

        subjectRepository.deleteById(subjectId);

        List<Subject> subjectList = subjectRepository.findBySemester(semester);

        List<UpdateTimetableResponseDto.updateResponseDto> updateResponseDtoLists = new ArrayList<>();
        subjectList.forEach(subject -> {
            UpdateTimetableResponseDto.updateResponseDto build = UpdateTimetableResponseDto.updateResponseDto.builder()
                    .updatedAt(subject.getUpdatedAt())
                    .subjectId(subject.getId())
                    .build();
            updateResponseDtoLists.add(build);
        });
        return UpdateTimetableResponseDto.updateResponseDtoList.builder().updateResponseDtos(updateResponseDtoLists).build();
    }

    /*@Transactional
    public void deleteTimetable(Long stuId, Integer grade, Integer semester, Long subjectId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        Optional<Semester> optionalSemester = semesterRepository.findByStudentAndGradeAndSemester(student, grade, semester);
        Semester semesterE = optionalSemester.get();
        Subject subject = subjectRepository.findByIdAndSemester_id(subjectId, semesterE.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));
        subjectRepository.delete(subject);
    }

     */
}
