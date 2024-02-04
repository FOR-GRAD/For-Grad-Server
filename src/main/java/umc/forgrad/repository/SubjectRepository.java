package umc.forgrad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.forgrad.domain.Subject;
import umc.forgrad.domain.Timetable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByIdAndTimetable_id(Long id, Long timetableId);

    List<Subject> findByTimetable(Timetable timetable);

    @Query("SELECT SUM(s.credit) FROM Subject s WHERE s IN :subjects")
    Optional<Integer> sumCredits(@Param("subjects") List<Subject> subjectList);

}
