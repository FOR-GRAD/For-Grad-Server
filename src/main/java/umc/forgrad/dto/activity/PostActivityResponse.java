package umc.forgrad.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.enums.Award;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.domain.enums.CertificationType;

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
    public static class ActivityListDto {
        List<ActivityPreviewDto> activityList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityPreviewDto {

        Long id;

        String title;

        LocalDate startDate;

        LocalDate endDate;

        Award award;

        CertificationType certificationType;

        Integer volunteerHour;

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

        List<String> fileUrls;

        Award award;

        CertificationType certificationType;

        Integer volunteerHour;


    }




//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class VolunteersListDto {
//
//        private Long id;
//        private String title;
//
//    }
//
//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class VolunteersDetailDto {
//
//        private Long id;
//        private String title;
//
//        private String content;
//
//        private String prize;
//
//        private Category category;
//
//        private LocalDate startDate;
//
//        private LocalDate endDate;
//
//    }
//
//
//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class VolunteersPreviewDto {
//    }
}
