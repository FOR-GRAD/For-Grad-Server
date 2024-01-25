package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.forgrad.domain.Student;
import umc.forgrad.domain.common.BaseEntity;
import umc.forgrad.domain.mapping.SemesterSubject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Semester extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student; //Student 엔티티와 연결

    @OneToMany(mappedBy = "semester")
    private List<SemesterSubject> semesterSubjectList = new ArrayList<>();

}
