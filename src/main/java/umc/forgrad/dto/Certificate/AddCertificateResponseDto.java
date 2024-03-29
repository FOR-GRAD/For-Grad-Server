package umc.forgrad.dto.Certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCertificateResponseDto {
    private Long certificateId;
    private LocalDateTime createdAt;
}
