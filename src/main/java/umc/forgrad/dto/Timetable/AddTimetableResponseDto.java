package umc.forgrad.dto.Timetable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddTimetableResponseDto {
    private Integer grade;
    private Integer semester;
    private Long subjectId;
    private LocalDateTime createdAt;
}
