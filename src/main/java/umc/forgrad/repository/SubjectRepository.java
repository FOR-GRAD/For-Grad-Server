package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByIdAndSemester_id(Long id, Long Semester_id);
    List<Subject> findBySemester(Semester semester);

    List<Subject> findBySemester(Semester semester);

    @Query("SELECT SUM(s.credit) FROM Subject s WHERE s IN :subjects")
    Optional<Integer> sumCredits(@Param("subjects") List<Subject> subjectList);

}
