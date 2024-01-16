package umc.forgrad.converter;

import umc.forgrad.dto.TestResponse;

public class TestConverter {

    public static TestResponse.TestDto toTestDto() {
        return TestResponse.TestDto.builder()
                .testString("Test!")
                .build();
    }

}
