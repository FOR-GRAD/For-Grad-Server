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

    private String prize;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Category category; //CERTIFICATIONS, COMPETITIONS, VOLUNTEERS, AWARDS;


}
