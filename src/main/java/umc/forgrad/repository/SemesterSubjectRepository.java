package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.List;

public interface SemesterSubjectRepository extends JpaRepository<SemesterSubject, Long> {

    List<SemesterSubject> findBySemesterIn(List<Semester> semester);

}
