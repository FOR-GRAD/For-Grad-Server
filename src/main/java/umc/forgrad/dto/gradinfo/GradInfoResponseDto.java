package umc.forgrad.dto.gradinfo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

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

    @Getter
    @Builder
    public static class MyGradesInfoDto {

        private Map<String, GradesListDtoAndTotalDto> myGradesInfoListDto;

    }

    @Getter
    @Builder
    public static class GradesListDtoAndTotalDto {
        private List<GradesDto> gradesDtoList;

        private GradesTotalDto gradesTotalDto;
    }

    @Getter
    @Builder
    public static class GradesDto {
        private String classification; // 선필교, 전필, 일선, ...

        private String subjectName; // 교과명

        private String credits; // 학점

        private String grade; // 성적(A+, A, B, ...)
    }

    @Getter
    @Builder
    public static class GradesTotalDto {
        private String appliedCredits; // 신청 학점

        private String acquiredCredits; // 취득 학점

        private String totalGrade; // 평점총계

        private String averageGrade; // 평균평점

        private String percentile; // 백분위
    }

    @Getter
    @Builder
    public static class CompletionStatusDto {
        private List<String> titleList;

        private List<Map<String, List<String>>> completionDtoMap;
    }

}
