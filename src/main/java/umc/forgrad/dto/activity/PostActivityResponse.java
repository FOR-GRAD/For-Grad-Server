package umc.forgrad.dto.activity;

import lombok.*;
import umc.forgrad.domain.enums.Award;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.domain.enums.CertificationType;
import umc.forgrad.dto.GetFileIdAndUrl;

import java.time.LocalDate;
import java.util.List;

public class PostActivityResponse {

    @Builder
    @Getter
    public static class RegistActivityResultDto {

        private Long id;
        private String title;

    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityDetailDto {

        Long id;
        String title;

        String content;

        Category category;

        LocalDate startDate;

        LocalDate endDate;

//        List<String> fileUrls;
        List<GetFileIdAndUrl> fileUrls;

        Award award;

        CertificationType certificationType;

        Integer volunteerHour;


    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityWithAccumulatedHours {

        Long id;

        String title;

        LocalDate startDate; // start_Date -> startDate
        LocalDate endDate; // end_Date -> endDate
        Award award;

        CertificationType certificationType; // certification_Type -> certificationType
        Integer volunteerHour; // volunteer_Hour -> volunteerHour
        Integer accum;

        Integer reindex;

    }

    @Builder
    @Getter
    public static class onlyAccumulatedList {
        List<ActivityWithAccumulatedHours> activityWithAccumulatedHours;

    }

}
