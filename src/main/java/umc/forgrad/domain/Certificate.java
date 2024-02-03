package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.forgrad.domain.common.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Certificate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student; //Student 엔티티와 연결

    public void update(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
