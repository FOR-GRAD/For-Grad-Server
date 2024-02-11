package umc.forgrad.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.converter.GradDateAndMessageConverter;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class GradDateServiceImpl implements GradDateService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto.GradUpdatedResponseDto updateGradDate(StudentRequestDto.GradDateRequestDto gradDateRequestDto, Long stuId) {

        Student student = studentRepository.findById(stuId).orElseThrow();

        // 졸업 예정일 & 응원의 한마디 업데이트
        student.updateGradDate(gradDateRequestDto.getGradDate());
        student.updateMessage(gradDateRequestDto.getMessage());

        Student updatedStudent = studentRepository.save(student);

        return GradDateAndMessageConverter.toUpdatedDto(updatedStudent);

    }


    @Override
    public StudentResponseDto.GradDateAndMessageResponseDto findGradDateAndMessage(Long stuId) {

        Student student = studentRepository.findById(stuId).orElseThrow();

        return GradDateAndMessageConverter.toQueryDto(student);

    }

}