package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Student;
import java.time.LocalDate;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
