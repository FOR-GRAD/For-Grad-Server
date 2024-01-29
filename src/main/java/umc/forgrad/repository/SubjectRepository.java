package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Semester;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
