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
    private String name;
    private LocalDate date;

    @Builder
    public ViewCertificateResponseDto(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
