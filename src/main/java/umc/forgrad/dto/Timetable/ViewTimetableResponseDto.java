package umc.forgrad.dto.Timetable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Subject;

@Getter
@NoArgsConstructor
public class ViewTimetableResponseDto {
    private String type;
    private String name;
    private Integer credit;

    @Builder
    public ViewTimetableResponseDto(String type, String name, Integer credit) {
        this.type = type;
        this.name = name;
        this.credit = credit;
    }
}
