package umc.forgrad.service.student;

import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;

public interface GradDateService {

    StudentResponseDto.GradDateResponseDto addGradDate(StudentRequestDto.GradDateRequestDto gradDateRequestDto, Long stuId);


}
