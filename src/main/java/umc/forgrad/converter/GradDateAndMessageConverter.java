package umc.forgrad.converter;

import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentResponseDto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GradDateAndMessageConverter {

    public static StudentResponseDto.GradUpdatedResponseDto toUpdatedDto(Student updatedStudent) {
        return StudentResponseDto.GradUpdatedResponseDto.builder()
                .gradDate(updatedStudent.getGradDate())
                .message(updatedStudent.getMessage())
                .build();
    }

    public static StudentResponseDto.GradDateAndMessageResponseDto toQueryDto(Student student) {
        return StudentResponseDto.GradDateAndMessageResponseDto.builder()
                .nowDate(LocalDate.now())
                .gradDate(student.getGradDate())
                .message(student.getMessage())
                .dDay(getBetween(student.getGradDate()))
                .build();
    }

    public static int getBetween(LocalDate gradDate) {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), gradDate);
    }

}
