package umc.forgrad.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class GradDateServiceImpl implements GradDateService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto.GradDateResponseDto updateGradDate(StudentRequestDto.GradDateRequestDto gradDateRequestDto, Long stuId) {

        Student student = studentRepository.findById(stuId).orElseThrow();

//        student.setGradDate(gradDateRequestDto.getGradDate());
//        student.setMessage(gradDateRequestDto.getMessage());
//
//        studentRepository.save(student);

        Student updatedStudent = Student.builder()
                .id(student.getId())
                .gradDate(gradDateRequestDto.getGradDate())
                .message(gradDateRequestDto.getMessage())
                .track1(student.getTrack1())
                .track2(student.getTrack2())
                .timetableList(student.getTimetableList())
                .activityList(student.getActivityList())
                .build();

        updatedStudent = studentRepository.save(updatedStudent);

        return StudentResponseDto.GradDateResponseDto.builder()
                .gradDate(updatedStudent.getGradDate())
                .message(updatedStudent.getMessage())
                .build();

    }

    @Override
    public StudentResponseDto.GradDateResponseDto findGradDate(Long stuId) {

        Student student = studentRepository.findById(stuId).orElseThrow();

        return StudentResponseDto.GradDateResponseDto.builder()
                .gradDate(student.getGradDate())
                .message(student.getMessage())
                .build();

    }
}