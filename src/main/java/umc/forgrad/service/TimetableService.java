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
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.Timetable;
import umc.forgrad.dto.Timetable.AddTimetableResponseDto;
import umc.forgrad.dto.Timetable.TimetableRequestDto;
import umc.forgrad.dto.Timetable.UpdateTimetableResponseDto;
import umc.forgrad.dto.Timetable.ViewTimetableResponseDto;
import umc.forgrad.repository.StudentRepository;
import umc.forgrad.repository.SubjectRepository;
import umc.forgrad.repository.TimetableRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;
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
            final Integer credit = Integer.parseInt(row.select("hakjum").text());
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

        Timetable timetable = TimetableConverter.toSemester(timetableDto.getSemesterDto(), student);
        Optional<Timetable> existingSemester = timetableRepository.findByStudentAndGradeAndSemester(student, timetableDto.getSemesterDto().getGrade(), timetableDto.getSemesterDto().getSemester());
        if (existingSemester.isPresent()) {
            timetable = existingSemester.get();
        } else {
            timetable = timetableRepository.save(timetable);
        }

        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();

        for (TimetableRequestDto.SubjectDto dto : subjectDtoList) {
            Subject subject = TimetableConverter.toSubject(dto, timetable);
            subjects.add(subject);
        }
        subjectRepository.saveAll(subjects);
        return TimetableConverter.toAddResultDto(subjects);
    }

    @Transactional
    public UpdateTimetableResponseDto.updateResponseDtoList updateTimetable(TimetableRequestDto.TimetableDto timetableDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));

        Optional<Timetable> optionalSemester = timetableRepository.findByStudentAndGradeAndSemester(student, timetableDto.getSemesterDto().getGrade(), timetableDto.getSemesterDto().getSemester());
        Timetable timetable = optionalSemester.get();
        List<TimetableRequestDto.SubjectDto> subjectDtoList = timetableDto.getSubjectDtoList();
        List<Subject> subjects = new ArrayList<>();
        for (TimetableRequestDto.SubjectDto dto : subjectDtoList) {
            Subject subject = subjectRepository.findByIdAndTimetableId(dto.getSubjuctId(), timetable.getId())
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
        Timetable semesterE = timetableRepository.findByStudentAndGradeAndSemester(student, grade, semester)
                .orElseThrow(() -> new IllegalArgumentException("해당 학년 학기가 존재하지 않습니다."));
        List<Subject> subjects = semesterE.getSubjectList();
        return TimetableConverter.toViewResultDto(subjects);
    }

    @Transactional
    public void deleteTimetable(Long stuId, Integer grade, Integer semester, Long subjectId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        Optional<Timetable> optionalSemester = timetableRepository.findByStudentAndGradeAndSemester(student, grade, semester);
        Timetable timetable = optionalSemester.get();
        Subject subject = subjectRepository.findByIdAndTimetableId(subjectId, timetable.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));
        subjectRepository.delete(subject);
    }
}
