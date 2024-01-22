package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
