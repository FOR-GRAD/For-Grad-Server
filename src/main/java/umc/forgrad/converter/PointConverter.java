package umc.forgrad.converter;

import umc.forgrad.dto.point.PointResponseDto;

import java.util.List;

public class PointConverter {

    public static PointResponseDto.MyPointResponseDto toMyPointResponseDto(int listSize, int pageSize, PointResponseDto.PointSummaryDto pointSummaryDto, List<PointResponseDto.PointDto> pointDtoList) {

        return PointResponseDto.MyPointResponseDto.builder()
                .listSize(listSize)
                .pageSize(pageSize)
                .pointSummaryDto(pointSummaryDto)
                .pointDtoList(pointDtoList)
                .build();

    }

}
