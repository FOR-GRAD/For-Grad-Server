package umc.forgrad.converter;

import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

import java.util.Map;

public class GradInfoConverter {

    public static GradInfoResponseDto.GradRequirementDto toGradRequirementDto(GradInfoResponseDto.CommonRequirmentsDto commonDto, GradInfoResponseDto.TrackRequirmentsDto trackDto) {

        return GradInfoResponseDto.GradRequirementDto.builder()
                .commonRequirmentsDto(commonDto)
                .trackRequirmentsDto(trackDto)
                .build();

    }

    public static GradInfoResponseDto.MyGradesInfoDto toMyGradesListMapDto(Map<String, GradInfoResponseDto.GradesListDtoAndTotalDto> myGradesTotalMap) {

        return GradInfoResponseDto.MyGradesInfoDto.builder()
                .myGradesInfoListDto(myGradesTotalMap)
                .build();
    }

}
