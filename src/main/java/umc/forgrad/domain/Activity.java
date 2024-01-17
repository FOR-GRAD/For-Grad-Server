package umc.forgrad.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.common.BaseEntity;
import umc.forgrad.domain.enums.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    private String content;

    private String image_url;

    @OneToMany(mappedBy = "activity")
    private List<ActivityFile> fileList = new ArrayList<>();

    public void addFileList(ActivityFile activityFile) {
        fileList.add(activityFile);
        activityFile.createActivity(this);
    }




    private String prize;

    private LocalDate startDate;

    private LocalDate endDate;

    private Category category; //CERTIFICATIONS, COMPETITIONS, VOLUNTEERS, AWARDS;


}
