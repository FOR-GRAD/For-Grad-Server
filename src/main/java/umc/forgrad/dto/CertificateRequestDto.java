package umc.forgrad.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CertificateRequestDto {
    private String name;
    private LocalDate date;
    private Student student;

    @Builder
    public CertificateRequestDto(String name, LocalDate date, Student student) {
        this.name = name;
        this.date = date;
        this.student = student;
    }
}
