package umc.forgrad.service.activityService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.activity.PostActivityResponse;
import umc.forgrad.exception.GeneralException;
import umc.forgrad.repository.ActivityRepository;
import umc.forgrad.repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ActivityQueryService {
    private final ActivityRepository activityRepository;
    private final StudentRepository studentRepository;
    public Page<Activity> getCareerList(Category category, Pageable pageable) throws IOException{

        Page<Activity> allByCategoryOrderByStartDateDesc = activityRepository.findAllByCategoryOrderByStartDateDesc(category, pageable);
        return allByCategoryOrderByStartDateDesc;


    }
    public Activity findActivity(Long activityId) throws IOException {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ACTIVITY_NOT_FOUND));
    }



    //수정중

    public List<PostActivityResponse.ActivityWithAccumulatedHours> getActivities(Category category, Long studentId) throws IOException{
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        System.out.println(studentId);
        List<Activity> activities = activityRepository.getActivitiesWithAccumulatedHours(category, studentId );

        return createActivitiesAccumulated(activities);
    }

    public List<PostActivityResponse.ActivityWithAccumulatedHours> getActivitiesByTitleAndCategory(String title, Category category, Long studentId) {
        List<Activity> activities = activityRepository.findByTitleContainingAndCategoryAndStudentId(title, category, studentId);
        return createActivitiesAccumulated(activities);
    }

    private List<PostActivityResponse.ActivityWithAccumulatedHours> createActivitiesAccumulated(List<Activity> activities) {
        int totalActivities = activities.size();

        List<PostActivityResponse.ActivityWithAccumulatedHours> result = new ArrayList<>();
        int accumulatedHours = 0;
        int toIndex= totalActivities;



        for (int i = 0; i < totalActivities; i++) {
            Activity activity = activities.get(i);
            accumulatedHours += activity.getVolunteerHour();

            result.add(PostActivityResponse.ActivityWithAccumulatedHours.builder()
                    .id(activity.getId())
                    .title((activity.getTitle()))
                    .accum(accumulatedHours)
                    .startDate(activity.getStartDate())
                    .endDate(activity.getEndDate())
                    .award(activity.getAward())
                    .certificationType(activity.getCertificationType())
                    .volunteerHour(activity.getVolunteerHour())
                    .reindex(toIndex)
                    .build()
            );
            toIndex -= 1;
            }



        return result;
    }








}
