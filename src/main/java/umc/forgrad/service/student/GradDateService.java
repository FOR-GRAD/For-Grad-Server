package umc.forgrad.service.student;

import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;

public interface GradDateService {

    StudentResponseDto.GradUpdatedResponseDto updateGradDate(StudentRequestDto.GradDateRequestDto gradDateRequestDto, Long stuId);

    StudentResponseDto.GradDateAndMessageResponseDto findGradDateAndMessage(Long stuId);
}
