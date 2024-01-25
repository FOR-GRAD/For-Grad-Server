package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.forgrad.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
