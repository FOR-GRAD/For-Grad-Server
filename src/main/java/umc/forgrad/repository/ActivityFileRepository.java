package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.ActivityFile;

import java.util.List;

public interface ActivityFileRepository extends JpaRepository<ActivityFile, Long> {



}
