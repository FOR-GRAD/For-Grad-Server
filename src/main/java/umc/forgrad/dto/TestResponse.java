package umc.forgrad.dto;

import lombok.Builder;
import lombok.Getter;

public class TestResponse {

    @Builder
    @Getter
    public static class TestDto {
        String testString;
    }

}
