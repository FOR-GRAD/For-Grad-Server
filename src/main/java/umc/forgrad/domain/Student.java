package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.forgrad.domain.mapping.Semester;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student {

    @Id
    private Long id; // 학번

    private LocalDate gradDate; // 졸업 예정일

    @Column(length = 20)
    private String message; // 응원의 한마디

    private String track1; // 1트랙

    private String track2; // 2트랙

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Semester> semesterList = new ArrayList<>();

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<Activity> activityList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Free free;

}
