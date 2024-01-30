package umc.forgrad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.activity.PostActivityResponse;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByCategoryOrderByStartDateDesc(Category category, Pageable pageable);

    int countByCategory(Category category);

    @Query("SELECT a FROM Activity a " +
            "WHERE (a.category = :category and a.student.id = :studentId)" +
            "ORDER BY a.startDate DESC")
    List<Activity> getActivitiesWithAccumulatedHours(@Param("category") Category category, Long studentId);

    List<Activity> findByTitleContainingAndCategoryAndStudentId(String title, Category category, Long studentId);







}
