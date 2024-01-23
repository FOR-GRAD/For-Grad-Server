package umc.forgrad.converter;

import umc.forgrad.dto.gradinfo.GradInfoResponseDto;

public class GradInfoConverter {

    public static GradInfoResponseDto.GradRequirementDto toGradRequirementDto(GradInfoResponseDto.CommonRequirmentsDto commonDto, GradInfoResponseDto.TrackRequirmentsDto trackDto) {

        return GradInfoResponseDto.GradRequirementDto.builder()
                .commonRequirmentsDto(commonDto)
                .trackRequirmentsDto(trackDto)
                .build();

    }

}
