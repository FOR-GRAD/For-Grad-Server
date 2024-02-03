package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Student;

import java.util.List;
import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Semester findByStudentAndGradeAndSemester(Student student, Integer grade, Integer semester);

}
