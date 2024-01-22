package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.forgrad.domain.common.BaseEntity;
import umc.forgrad.dto.CertificateRequestDto;

import java.time.LocalDate;

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

    public Certificate(CertificateRequestDto certificateRequestDto) {
        this.name = certificateRequestDto.getName();
        this.date = certificateRequestDto.getDate();
        this.student = certificateRequestDto.getStudent();
    }
}
