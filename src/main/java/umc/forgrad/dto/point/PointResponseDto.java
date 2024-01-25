package umc.forgrad.dto.point;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PointResponseDto {

    @Getter
    @Builder
    public static class MyPointResponseDto {
        private int listSize;

        private int pageSize;

        private PointSummaryDto pointSummaryDto;

        private List<PointDto> pointDtoList;
    }

    @Getter
    @Builder
    public static class PointSummaryDto {
        private String semesterPoints; // 이번 학기 취득 비교과 포인트

        private String carryoverPoints; // 지난 학기 이월 비교과 포인트

        private String accumulatedPoints; // 전체 누적 포인트

        private String graduationPoints; // 졸업 인정 비교과 포인트
    }

    @Getter
    @Builder
    public static class PointDto {
        private String title; // 프로그램명

        private String rewardPoints; // 해당 비교과 프로그램에서 받은 포인트

        private String accumulatedPoints; // 누적 포인트

        private String date; // 날짜
    }

}
