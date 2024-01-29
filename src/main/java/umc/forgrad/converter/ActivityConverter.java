package umc.forgrad.converter;

import lombok.Builder;
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

        List<PostActivityResponse.ActivityPreviewDto> collect = activityList.stream()
                .map(ActivityConverter::activityPreviewDto).collect(Collectors.toList());

        return PostActivityResponse.ActivityListDto.builder()
                .isLast(activityList.isLast())
                .isFirst(activityList.isFirst())
                .totalPage(activityList.getNumber())
                .totalPage(activityList.getTotalPages())
                .totalElements(activityList.getTotalElements())
                .listSize(activityList.getSize())
                .activityList(collect)
                .build();
    }



    public static PostActivityResponse.responseAccum convertToResponseAccum(Page<PostActivityResponse.ActivityWithAccumulatedHours> page) {
        List<PostActivityResponse.ActivityWithAccumulatedHours> activityList = page.stream()
                .map(ActivityConverter::toDto)  // ActivityWithAccumulatedHours 객체를 DTO로 변환하는 메서드
                .collect(Collectors.toList());

        return PostActivityResponse.responseAccum.builder()
                .activityWithAccumulatedHours(activityList)
                .listSize(activityList.size())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .nowPage(page.getNumber())
                .build();
    }

    private static PostActivityResponse.ActivityWithAccumulatedHours toDto(PostActivityResponse.ActivityWithAccumulatedHours activityWithAccumulatedHours) {

        return PostActivityResponse.ActivityWithAccumulatedHours.builder()
                .id(activityWithAccumulatedHours.getId())
                .title(activityWithAccumulatedHours.getTitle())
                .startDate(activityWithAccumulatedHours.getStartDate())
                .endDate(activityWithAccumulatedHours.getEndDate())
                .volunteerHour(activityWithAccumulatedHours.getVolunteerHour())
                .award(activityWithAccumulatedHours.getAward())
                .certificationType(activityWithAccumulatedHours.getCertificationType())
                .accum(activityWithAccumulatedHours.getAccum())
                .reindex(activityWithAccumulatedHours.getReindex())
                .build();
    }

    public static PostActivityResponse.onlyAccumulatedList activityResult(List<PostActivityResponse.ActivityWithAccumulatedHours> activity) {
        return PostActivityResponse.onlyAccumulatedList.builder()
                .activityWithAccumulatedHours(activity)
                .build();
    }
//    private static PostActivityResponse.ActivityWithAccumulatedHours toDto(Activity activity) {
//
//        return PostActivityResponse.ActivityWithAccumulatedHours.builder()
//                .id(activityWithAccumulatedHours.getId())
//                .title(activityWithAccumulatedHours.getTitle())
//                .startDate(activityWithAccumulatedHours.getStartDate())
//                .endDate(activityWithAccumulatedHours.getEndDate())
//                .volunteerHour(activityWithAccumulatedHours.getVolunteerHour())
//                .award(activityWithAccumulatedHours.getAward())
//                .certificationType(activityWithAccumulatedHours.getCertificationType())
//                .accum(activityWithAccumulatedHours.getAccum())
//                .reindex(activityWithAccumulatedHours.getReindex())
//                .build();
//    }

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

//    public static <PostActivityResponse.only> createActivitiesAccumulated(List<Activity> activities) {
//        int totalActivities = activities.size();
//
//        List<PostActivityResponse.ActivityWithAccumulatedHours> result = new ArrayList<>();
//        int accumulatedHours = 0;
//        int toIndex= totalActivities;
//
//        List<PostActivityResponse.ActivityPreviewDto> collect = activities.stream()
//                .map(ActivityConverter::activityPreviewDto).collect(Collectors.toList());
//
//
//
//        for (int i = 0; i < totalActivities; i++) {
//            Activity activity = activities.get(i);
//            accumulatedHours += activity.getVolunteerHour();
//
//            result.add(PostActivityResponse.ActivityWithAccumulatedHours.builder()
//                    .id(activity.getId())
//                    .title((activity.getTitle()))
//                    .accum(accumulatedHours)
//                    .startDate(activity.getStartDate())
//                    .endDate(activity.getEndDate())
//                    .award(activity.getAward())
//                    .certificationType(activity.getCertificationType())
//                    .volunteerHour(activity.getVolunteerHour())
//                    .reindex(toIndex)
//                    .build()
//            );
//        }
//        toIndex -= 1;
//
//
//        return result;
//    }
}
