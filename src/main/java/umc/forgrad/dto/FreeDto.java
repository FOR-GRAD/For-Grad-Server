package umc.forgrad.dto;

import lombok.Builder;
import lombok.Getter;

public class FreeDto {

    @Getter
    public static class MemoRequestDto{
        private String memo;
    }

    @Getter
    @Builder
    public static class MemoResponseDto{
        private String memo;
    }
}
