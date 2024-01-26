package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.List;

@Repository
public interface SemesterSubjectRepository extends JpaRepository<SemesterSubject, Long> {
    List<SemesterSubject> findBySemester(Semester semester);
}
