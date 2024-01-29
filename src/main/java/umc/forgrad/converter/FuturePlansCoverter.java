package umc.forgrad.converter;

import umc.forgrad.domain.Subject;
import umc.forgrad.dto.student.StudentResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuturePlansCoverter {

    public static Map<String, List<StudentResponseDto.FutureTimeTableDto>> toFutureTimeTableDto(String semester, List<Subject> subjectList) {

        Map<String, List<StudentResponseDto.FutureTimeTableDto>> listMap = new HashMap<>();
        List<StudentResponseDto.FutureTimeTableDto> futureTimeTableDtoList = new ArrayList<>();

        subjectList.forEach(subject -> futureTimeTableDtoList.add(StudentResponseDto.FutureTimeTableDto.builder()
                .majorType(subject.getType())
                .subject(subject.getName())
                .grades(subject.getCredit())
                .build()
        ));

        listMap.put(semester, futureTimeTableDtoList);

        return listMap;

    }

}
