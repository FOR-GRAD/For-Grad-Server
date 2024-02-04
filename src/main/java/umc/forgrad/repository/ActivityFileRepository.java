package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.ActivityFile;

import java.util.List;

@Repository
public interface ActivityFileRepository extends JpaRepository<ActivityFile, Long> {

    List<ActivityFile> findAllByActivityId(Long id);

    @Query("select af.id from ActivityFile af where af.activity.id = :id")
    List<Long> findAllId(@Param("id") Long id);

    @Modifying
    @Query("delete from ActivityFile af where af.id in :ids")
    void deleteAllByActivityIds(List<Long> ids);
}
