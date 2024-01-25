package umc.forgrad.dto.Timetable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Subject;

@Getter
@NoArgsConstructor
public class ViewTimetableResponseDto {
    private Integer grade;
    private Integer semester;
    private String type;
    private String name;
    private Integer credit;

    @Builder
    public ViewTimetableResponseDto(Semester semester, Subject subject) {
        this.grade = semester.getGrade();
        this.semester = semester.getSemester();
        this.type = subject.getType();
        this.name = subject.getName();
        this.credit = subject.getCredit();
    }
}
