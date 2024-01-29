package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.forgrad.domain.common.BaseEntity;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Subject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer credit;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<SemesterSubject> semesterSubjectList = new ArrayList<>();

    public void update(String type, String name, Integer credit) {
        this.type = type;
        this.name = name;
        this.credit = credit;
    }

}
