package umc.forgrad.dto.Timetable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ViewTimetableResponseDto {
    private Long subjectId;
    private String type;
    private String name;
    private Integer credit;

    @Builder
    public ViewTimetableResponseDto(Long subjectId, String type, String name, Integer credit) {
        this.subjectId = subjectId;
        this.type = type;
        this.name = name;
        this.credit = credit;
    }
}
