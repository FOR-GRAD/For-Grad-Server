package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Free;


@Repository
public interface FreeRepository extends JpaRepository<Free, Long> {
//    Free save(Free free);
//    Optional<Free> findById(Long id);
//    List<Free> findAll();

    Free findByStudentId(Long stuid);
}