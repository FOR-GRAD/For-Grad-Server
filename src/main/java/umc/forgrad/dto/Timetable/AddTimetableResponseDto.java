package umc.forgrad.dto.Timetable;

import lombok.Getter;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class AddTimetableResponseDto {
    @Getter
    @Builder
    public static class addResponseDto {
        private Long subjectId;
        private LocalDateTime createdAt;
    }
    @Getter
    @Builder
    public static class addResponseDtoList {
        private List<addResponseDto> addResponseDtos;
    }
}
