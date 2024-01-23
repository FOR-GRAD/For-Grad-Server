package umc.forgrad.converter;

import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.CertificateRequestDto;
import umc.forgrad.dto.CertificateResponseDto;

import java.time.LocalDateTime;

public class CertificateConverter {
    public static CertificateResponseDto toAddResultDto(Certificate certificate) {
        return CertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Certificate toCertificate(CertificateRequestDto certificateRequestDto, Student student) {
        return Certificate.builder()
                .name(certificateRequestDto.getName())
                .date(certificateRequestDto.getDate())
                .student(student)
                .build();
    }
}
