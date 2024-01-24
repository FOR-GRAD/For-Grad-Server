package umc.forgrad.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Certificate;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddCertificateRequestDto {
    private String name;
    private LocalDate date;

    @Builder
    public AddCertificateRequestDto(Certificate certificate) {
        this.name = certificate.getName();
        this.date = certificate.getDate();
    }
}
