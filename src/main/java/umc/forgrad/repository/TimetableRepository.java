package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Timetable;

import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Optional<Timetable> findByStudentAndGradeAndSemester(Student student, Integer grade, Integer semester);

}
