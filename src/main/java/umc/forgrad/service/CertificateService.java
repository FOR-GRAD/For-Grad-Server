package umc.forgrad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.dto.CertificateRequestDto;
import umc.forgrad.repository.CertificateRepository;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;

    @Transactional
    public Certificate addCertificate(CertificateRequestDto certificateRequestDto) {
        return certificateRepository.save(CertificateConverter.toCertificate(certificateRequestDto));
    }

    /*public Certificate viewCertificate(Long stuId) {
        return certificateRepository.findById();

    }*/
}
