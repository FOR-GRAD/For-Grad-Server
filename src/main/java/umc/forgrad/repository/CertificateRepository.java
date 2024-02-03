package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findAllByStudent_id(Long stuId);
    Optional<Certificate> findByIdAndStudent(Long id, Student student);
    void deleteByIdAndStudent(Long id, Student student);
}
