package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
