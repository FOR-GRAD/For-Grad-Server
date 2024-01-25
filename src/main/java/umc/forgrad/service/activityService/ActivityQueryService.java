package umc.forgrad.service.activityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ActivityQueryService {
    private final ActivityRepository activityRepository;
    public Page<Activity> getCareerList(Category category, Pageable pageable){

        Page<Activity> byCategory = activityRepository.findAllByCategoryOrderByStartDateDesc(category, pageable);
        return byCategory;


    }
    public Activity findActivity(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + activityId));
    }

    public Integer sumHour(){
        Integer i = activityRepository.sumVolunteerHour();
        log.info(i.toString());

        return activityRepository.sumVolunteerHour();
    }


}
