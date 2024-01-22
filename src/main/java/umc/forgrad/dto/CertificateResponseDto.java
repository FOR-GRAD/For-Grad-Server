package umc.forgrad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.forgrad.domain.Certificate;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseDto {
    Long certificateId;
    LocalDateTime createdAt;

    public static CertificateResponseDto toAddResultDto(Certificate certificate) {
        return CertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
