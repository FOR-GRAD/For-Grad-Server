package umc.forgrad.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.enums.Award;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.domain.enums.CertificationType;

import java.time.LocalDate;


public class PostActivityRequest {

    @Getter
    public static class RegistActivity {


        @NotBlank(message = "활동명을 입력하세요.")
        @Size(min = 2, max = 20, message = "제목의 길이는 2~20글자까지 입력 가능합니다.")
        private String title;
        private String content;

        private String prize;

        private Category category;

        private LocalDate startDate;

        private LocalDate endDate;

        private Long studentId;

        private Integer volunteerHour;

        private Award award;

        private CertificationType certificationType;
    }
}
