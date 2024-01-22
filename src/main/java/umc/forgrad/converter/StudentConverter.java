package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentResponseDto;

public class StudentConverter {

    public static Student toStudent(Long id) {
        return Student.builder()
                .id(id)
                .build();
    }

    public static StudentResponseDto.LoginResponseDto toLoginResultDto(String loginResultMessage) {
        return StudentResponseDto.LoginResponseDto.builder()
                .message(loginResultMessage)
                .build();
    }

//    public static StudentResponseDto.HomeResponseDto toQueryHomeResultDto(StudentResponseDto.HomeResponseDto homeResponseDto) {
//        return StudentResponseDto.HomeResponseDto.builder()
//                .name(homeResponseDto.getName())
//                .track1(homeResponseDto.getTrack1())
//                .track2(homeResponseDto.getTrack2())
//                .status(homeResponseDto.getStatus())
//                .message(homeResponseDto.getMessage())
//                .trackRequirement1(homeResponseDto.getTrackRequirement1())
//                .trackRequirement2(homeResponseDto.getTrackRequirement2())
//                .futureTimeTableDto(homeResponseDto.getFutureTimeTableDto())
//                .build();
//    }

}
