package umc.forgrad.dto.Timetable;
import lombok.Getter;
import lombok.Builder;

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
    public static class TimetableDto {
        private SemesterDto semesterDto;
        private SubjectDto subjectDto;
    }
}
