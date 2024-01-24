package umc.forgrad.converter;

import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.AddCertificateRequestDto;
import umc.forgrad.dto.AddCertificateResponseDto;
import umc.forgrad.dto.ViewCertificateResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateConverter {
    public static AddCertificateResponseDto toAddResultDto(Certificate certificate) {
        return AddCertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static List<ViewCertificateResponseDto> toViewResultDto(List<Certificate> certificates) {
        return certificates.stream()
                .map(certificate -> ViewCertificateResponseDto.builder()
                        .name(certificate.getName())
                        .date(certificate.getDate())
                        .build())
                .collect(Collectors.toList());
    }

    public static Certificate toCertificate(AddCertificateRequestDto addCertificateRequestDto, Student student) {
        return Certificate.builder()
                .name(addCertificateRequestDto.getName())
                .date(addCertificateRequestDto.getDate())
                .student(student)
                .build();
    }
}
