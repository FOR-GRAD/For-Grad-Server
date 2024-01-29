package umc.forgrad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.activity.PostActivityResponse;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByCategoryOrderByStartDateDesc(Category category, Pageable pageable);

    @Query("select sum(a.volunteerHour) from Activity a where a.category = 'volunteers'")
    Integer sumVolunteerHour();





    int countByCategory(Category category);

    @Query("SELECT a FROM Activity a " +
            "WHERE a.category = :category " +
            "ORDER BY a.startDate DESC")
    List<Activity> getActivitiesWithAccumulatedHours(@Param("category") Category category);

    List<Activity> findByTitleContainingAndCategory(String title, Category category);







}
