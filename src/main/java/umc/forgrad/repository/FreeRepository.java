package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Free;
import umc.forgrad.domain.FreePk;

import java.util.List;
import java.util.Optional;


@Repository
public interface FreeRepository extends JpaRepository<Free, FreePk> {
//    Free save(Free free);
//    Optional<Free> findById(Long id);
//    List<Free> findAll();

    Free findByStuId(Long stuId);
}