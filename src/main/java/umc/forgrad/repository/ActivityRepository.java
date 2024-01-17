package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {


}
