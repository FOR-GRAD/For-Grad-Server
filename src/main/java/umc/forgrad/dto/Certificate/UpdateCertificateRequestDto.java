package umc.forgrad.dto.Certificate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Certificate;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateCertificateRequestDto {
    private Long certificateId;
    private String name;
    private LocalDate date;

    @Builder
    public UpdateCertificateRequestDto(Certificate certificate) {
        this.certificateId = certificate.getId();
        this.name = certificate.getName();
        this.date = certificate.getDate();
    }
}
