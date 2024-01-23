package umc.forgrad.dto.gradinfo;

import lombok.Builder;
import lombok.Getter;

public class GradInfoResponseDto {

    @Getter
    @Builder
    public static class GradRequirementDto {
        private CommonRequirmentsDto commonRequirmentsDto;

        private TrackRequirmentsDto trackRequirmentsDto;
    }

    @Getter
    @Builder
    public static class CommonRequirmentsDto {
        private String registration; // 등록

        private String grades; // 학점

        private String point; // 비교과 포인트

        private String scores; // 성적
    }

    @Getter
    @Builder
    public static class TrackRequirmentsDto {
        private String track1; // 1트랙 이름

        private String track2; // 2트랙 이름

        private String trackRequirement1; // 1트랙 졸업요건

        private String trackRequirement2; // 2트랙 졸업요건

        private String note1; // 1트랙 졸업요건 비고

        private String note2; // 2트랙 졸업요건 비고
    }

}
