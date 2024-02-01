package umc.forgrad.dto.Timetable;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateTimetableResponseDto {
    @Getter
    @Builder
    public static class updateResponseDto {
        private Long subjectId;
        private LocalDateTime updatedAt;
    }
    @Getter
    @Builder
    public static class updateResponseDtoList {
        private List<UpdateTimetableResponseDto.updateResponseDto> updateResponseDtos;
    }
}
