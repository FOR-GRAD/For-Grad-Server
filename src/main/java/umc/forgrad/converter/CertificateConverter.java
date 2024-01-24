package umc.forgrad.converter;

import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.AddCertificateRequestDto;
import umc.forgrad.dto.AddCertificateResponseDto;
import umc.forgrad.dto.ViewCertificateResponseDto;

import java.time.LocalDateTime;

public class CertificateConverter {
    public static AddCertificateResponseDto toAddResultDto(Certificate certificate) {
        return AddCertificateResponseDto.builder()
                .certificateId(certificate.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    /*
    public static ViewCertificateResponseDto toViewResultDto(Certificate certificate) {
        return ViewCertificateResponseDto.builder()
                .name(certificate.getName())
                .date(certificate.getDate())
                .build();
    }
*/
    public static Certificate toCertificate(AddCertificateRequestDto addCertificateRequestDto, Student student) {
        return Certificate.builder()
                .name(addCertificateRequestDto.getName())
                .date(addCertificateRequestDto.getDate())
                .student(student)
                .build();
    }
}
