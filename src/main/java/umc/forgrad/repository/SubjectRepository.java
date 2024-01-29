package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.forgrad.domain.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT SUM(s.credit) FROM Subject s WHERE s IN :subjects")
    Integer sumCredits(@Param("subjects") List<Subject> subjectList);

}