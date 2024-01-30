package umc.forgrad.dto.Certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.BindParam;
import umc.forgrad.domain.Certificate;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ViewCertificateResponseDto {
    private Long certificateId;
    private String name;
    private LocalDate date;

    @Builder
    public ViewCertificateResponseDto(Long certificateId, String name, LocalDate date) {
        this.certificateId = certificateId;
        this.name = name;
        this.date = date;
    }
}
