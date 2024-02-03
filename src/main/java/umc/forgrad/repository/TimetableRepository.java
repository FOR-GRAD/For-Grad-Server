package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Timetable findByStudentAndGradeAndSemester(Student student, Integer grade, Integer semester);

}
