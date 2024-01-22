package umc.forgrad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.forgrad.repository.CertificateRepository;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
}
