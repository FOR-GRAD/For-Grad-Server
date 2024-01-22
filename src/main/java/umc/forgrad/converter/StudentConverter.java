package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.domain.Subject;
import umc.forgrad.dto.student.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;

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

    public static List<StudentResponseDto.FutureTimeTableDto> toFutureTimeTableDto(List<Subject> subjectList) {

        List<StudentResponseDto.FutureTimeTableDto> futureTimeTableDtoList = new ArrayList<>();

        subjectList.forEach(subject -> futureTimeTableDtoList.add(StudentResponseDto.FutureTimeTableDto.builder()
                .majorType(subject.getType())
                .subject(subject.getName())
                .grades(subject.getCredit())
                .build()
        ));

        return futureTimeTableDtoList;

    }

}
