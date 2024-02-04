package umc.forgrad.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import software.amazon.ion.Decimal;
import umc.forgrad.domain.common.BaseEntity;
import umc.forgrad.domain.enums.Award;
import umc.forgrad.domain.enums.Category;
import umc.forgrad.domain.enums.CertificationType;
import umc.forgrad.dto.activity.PostActivityRequest;
import umc.forgrad.dto.activity.PostActivityResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Category category; //CERTIFICATIONS, COMPETITIONS, VOLUNTEERS, AWARDS;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;


    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityFile> fileList = new ArrayList<>();

    public void addFileList(ActivityFile activityFile) {
        fileList.add(activityFile);
        activityFile.createActivity(this);
    }


    ////////////////여기서부터 추가

    @Enumerated(EnumType.STRING)
    private Award award;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    private Integer volunteerHour;


    public void update(PostActivityRequest.UpdateDto updateDto) {
        if (updateDto != null) {
            Optional.ofNullable(updateDto.getTitle()).ifPresent(this::updateTitle);
            Optional.ofNullable(updateDto.getContent()).ifPresent(this::updateContent);
            Optional.ofNullable(updateDto.getStartDate()).ifPresent(this::updatestartDate);
            Optional.ofNullable(updateDto.getEndDate()).ifPresent(this::updateEndDate);
            Optional.ofNullable(updateDto.getCategory()).ifPresent(this::updateCategory);
            Optional.ofNullable(updateDto.getAward()).ifPresent(this::updateAward);
            Optional.ofNullable(updateDto.getCertificationType()).ifPresent(this::updateCertificationType);
            Optional.ofNullable(updateDto.getVolunteerHour()).ifPresent(this::updateVolunteerHour);
        }
    }

    private void updateTitle(String title) {
        this.title = title;
    }

    private void updateContent(String content) {
        this.content = content;
    }

    private void updatestartDate(LocalDate startDate) {
        this.startDate = startDate;

    }

    private void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private void updateCategory(Category category) {
        this.category = category;
    }

    private void updateAward(Award award) {
        this.award = award;
    }

    private void updateCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    private void updateVolunteerHour(Integer volunteerHour) {
        this.volunteerHour = volunteerHour;
    }

}
