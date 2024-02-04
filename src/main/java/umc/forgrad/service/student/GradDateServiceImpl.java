package umc.forgrad.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.student.StudentRequestDto;
import umc.forgrad.dto.student.StudentResponseDto;
import umc.forgrad.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class GradDateServiceImpl implements GradDateService{

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto.GradDateResponseDto addGradDate(StudentRequestDto.GradDateRequestDto gradDateRequestDto, Long stuId){

        Student student = studentRepository.findById(stuId).orElseThrow();

//        student.setGradDate(gradDateRequestDto.getGradDate());
//        student.setMessage(gradDateRequestDto.getMessage());
//
//        studentRepository.save(student);

        Student newStudent = Student.builder()
                .id(student.getId())
                .gradDate(gradDateRequestDto.getGradDate())
                .message(gradDateRequestDto.getMessage())
                .track1(student.getTrack1())
                .track2(student.getTrack2())
                .timetableList(student.getTimetableList())
                .activityList(student.getActivityList())
                .free(student.getFree())
                .build();

        studentRepository.save(newStudent);

        return StudentResponseDto.GradDateResponseDto.builder()
                .gradDate(newStudent.getGradDate())
                .message(newStudent.getMessage())
                .build();

    }

}
