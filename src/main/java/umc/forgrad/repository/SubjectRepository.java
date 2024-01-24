package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
