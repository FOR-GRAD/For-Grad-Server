package umc.forgrad.converter;

import umc.forgrad.domain.Subject;
import umc.forgrad.dto.student.StudentResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuturePlansCoverter {

    public static Map<String, StudentResponseDto.FutureTimeTableDto> toFutureTimeTableDto(String semester, Integer sumCredits, List<Subject> subjectList) {

        Map<String, StudentResponseDto.FutureTimeTableDto> listMap = new HashMap<>();

        List<StudentResponseDto.TimeTableDto> timeTableDtoList = subjectList.stream()
                .map(subject -> StudentResponseDto.TimeTableDto.builder()
                        .majorType(subject.getType())
                        .subject(subject.getName())
                        .grades(subject.getCredit())
                        .build()
                )
                .toList();

        StudentResponseDto.FutureTimeTableDto futureTimeTableDto = toFutureTimeTableDto(sumCredits, timeTableDtoList);

        listMap.put(semester, futureTimeTableDto);

        return listMap;
    }

    private static StudentResponseDto.FutureTimeTableDto toFutureTimeTableDto(Integer sumCredits, List<StudentResponseDto.TimeTableDto> timeTableDtoList) {
        return StudentResponseDto.FutureTimeTableDto.builder()
                .timeTableDtoList(timeTableDtoList)
                .sumCredits(sumCredits)
                .build();
    }


}
