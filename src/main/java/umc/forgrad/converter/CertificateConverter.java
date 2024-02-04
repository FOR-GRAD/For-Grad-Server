package umc.forgrad.converter;

import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.Certificate.*;

import java.util.List;
import java.util.stream.Collectors;

public class CertificateConverter {
    public static AddCertificateResponseDto toAddResultDto(Certificate certificate) {
        return AddCertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .createdAt(certificate.getCreatedAt())
                .build();
    }
    public static UpdateCertificateResponseDto toUpdateResultDto(Certificate certificate) {
        return UpdateCertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .updatedAt(certificate.getUpdatedAt())
                .build();
    }

    public static List<ViewCertificateResponseDto> toViewResultDto(List<Certificate> certificates) {
        return certificates.stream()
                .map(certificate -> ViewCertificateResponseDto.builder()
                        .certificateId(certificate.getId())
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
