package umc.forgrad.repository;

import java.util.List;
import java.util.Optional;

public interface FreeRepository {
    Free save(Free free);
    Optional<Free> findById(Long id);
    List<Free> findAll();
}