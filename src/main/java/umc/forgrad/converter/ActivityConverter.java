package umc.forgrad.converter;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import umc.forgrad.controller.ActivityController;
import umc.forgrad.domain.Activity;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.dto.activity.PostActivityResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityConverter {
    public static Activity toActivity(PostActivityRequest.RegistActivity registActivity, List<MultipartFile> multipartFiles, Student student) {

        Activity activity = Activity.builder()
                .title(registActivity.getTitle())
                .content(registActivity.getContent())
                .category(registActivity.getCategory())
                .startDate(registActivity.getStartDate())
                .endDate(registActivity.getEndDate())
                .volunteerHour(registActivity.getVolunteerHour())
                .award(registActivity.getAward())
                .certificationType(registActivity.getCertificationType())
                .fileList(new ArrayList<>())
                .student(student)
                .build();

        return  activity;
    }

    public static PostActivityResponse.RegistActivityResultDto activityResultDto(Activity activity){

        return PostActivityResponse.RegistActivityResultDto.builder()
                .title(activity.getTitle())
                .id(activity.getId())
                .build();
    }

    public static PostActivityResponse.ActivityPreviewDto activityPreviewDto(Activity activity){
        return PostActivityResponse.ActivityPreviewDto.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .startDate(activity.getStartDate())
                .endDate(activity.getEndDate())
                .volunteerHour(activity.getVolunteerHour())
                .award(activity.getAward())
                .certificationType(activity.getCertificationType())
                .build();
    }

    public static PostActivityResponse.ActivityListDto activityListDto(Page<Activity> activityList) {

        List<PostActivityResponse.ActivityPreviewDto> collect = activityList.stream().map(ActivityConverter::activityPreviewDto).collect(Collectors.toList());

        return PostActivityResponse.ActivityListDto.builder()
                .isLast(activityList.isLast())
                .isFirst(activityList.isFirst())
                .totalPage(activityList.getTotalPages())
                .totalElements(activityList.getTotalElements())
                .listSize(activityList.getSize())
                .activityList(collect)
                .build();
    }

    public static PostActivityResponse.ActivityDetailDto activityDetailDto(Activity activity, List<String> fileUrls){

        return PostActivityResponse.ActivityDetailDto.builder()
                .fileUrls(fileUrls)
                .title(activity.getTitle())
                .startDate(activity.getStartDate())
                .endDate(activity.getEndDate())
                .category(activity.getCategory())
                .content(activity.getContent())
                .award(activity.getAward())
                .certificationType(activity.getCertificationType())
                .volunteerHour(activity.getVolunteerHour())
                .build();
    }

}
