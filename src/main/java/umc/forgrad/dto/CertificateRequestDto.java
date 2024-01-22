package umc.forgrad.dto;

import lombok.Getter;
import umc.forgrad.domain.Student;

import java.time.LocalDate;

@Getter
public class CertificateRequestDto {
    private String name;
    private LocalDate date;
    private Student student;
}
