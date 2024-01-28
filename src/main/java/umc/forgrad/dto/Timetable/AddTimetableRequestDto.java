package umc.forgrad.dto.Timetable;
import lombok.Getter;
import lombok.Builder;

import java.util.List;

public class AddTimetableRequestDto {
    @Getter
    @Builder
    public static class SemesterDto {
        private Integer grade;
        private Integer semester;
    }

    @Getter
    @Builder
    public static class SubjectDto {
        private String type;
        private String name;
        private Integer credit;
    }
    @Getter
    @Builder
    public static class SubjectDtoList {
        private List<SubjectDto> subjectDtos;
    }

    @Getter
    @Builder
    public static class TimetableDto {
        private SemesterDto semesterDto;
        private List<SubjectDto> subjectDtoList;
    }
    @Getter
    @Builder
    public static class HakkiDto {
        private String hakkiText;
        private Integer hakkiNum;
    }
    @Getter
    @Builder
    public static class TrackDto {
        private String trackName;
        private String trackCode;
    }
    @Getter
    @Builder
    public static class SearchSubjectDto {
        private Integer searchGrade;
        private String searchType;
        private String searchName;
        private Integer searchCredit;
    }
}
