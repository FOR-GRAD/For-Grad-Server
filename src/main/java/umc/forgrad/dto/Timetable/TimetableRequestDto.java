package umc.forgrad.dto.Timetable;
import lombok.Getter;
import lombok.Builder;

import java.util.List;

public class TimetableRequestDto {
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
        private String hakkiNum;
        private String hakkiText;
    }
    @Getter
    @Builder
    public static class TrackDto {
        private String trackCode;
        private String trackName;
    }
    @Getter
    @Builder
    public static class SearchSubjectDto {
        private String searchGrade;
        private String searchType;
        private String searchName;
        private String searchCredit;
    }
}
