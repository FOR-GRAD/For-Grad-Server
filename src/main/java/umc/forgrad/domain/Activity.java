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


    public void update(PostActivityRequest.UpdateDto updateDto)
    {
        updateTitle(updateDto.getTitle());
        updateContent(updateDto.getContent());
        updatestartDate(updateDto.getStartDate());
        updateEndDate(updateDto.getEndDate());
        updateCategory(updateDto.getCategory());
        updateAward(updateDto.getAward());
        updateCertificationType(updateDto.getCertificationType());
        updateVolunteerHour(updateDto.getVolunteerHour());

    }

    private void updateTitle(String title) {
        if(title!=null){
            this.title = title;
        }
    }
    private void updateContent(String content){
        if(content!=null){
            this.content = content;
        }
    }

    private void updatestartDate(LocalDate startDate) {
        if(startDate!=null){
            this.startDate = startDate;
        }
    }

    private void updateEndDate(LocalDate endDate) {
        if (endDate!=null) {
            this.endDate = endDate;
        }
    }

    private void updateCategory(Category category){
        if (category != null) {
            this.category = category;
        }
    }
    private void updateAward(Award award) {
        if (award != null) {
            this.award = award;

        }
    }

    private void updateCertificationType(CertificationType certificationType) {
        if(certificationType!=null){
        this.certificationType = certificationType;

        }

    }

    private void updateVolunteerHour(Integer volunteerHour) {
        if(volunteerHour!=null){
            this.volunteerHour = volunteerHour;

        }
    }

}
