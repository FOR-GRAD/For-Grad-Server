package umc.forgrad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.AddCertificateRequestDto;
import umc.forgrad.dto.ViewCertificateResponseDto;
import umc.forgrad.repository.CertificateRepository;
import umc.forgrad.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Certificate addCertificate(AddCertificateRequestDto addCertificateRequestDto, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        return certificateRepository.save(CertificateConverter.toCertificate(addCertificateRequestDto, student));
    }

    @Transactional(readOnly = true)
    public List<Certificate> viewCertificate(Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        return certificateRepository.findAllByStudent_id(stuId);
    }
}
