package umc.forgrad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.Certificate.AddCertificateRequestDto;
import umc.forgrad.dto.Certificate.UpdateCertificateRequestDto;
import umc.forgrad.repository.CertificateRepository;
import umc.forgrad.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public List<Certificate> addCertificates(List<AddCertificateRequestDto> addCertificateRequestDtos, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        List<Certificate> certificates = new ArrayList<>();
        for (AddCertificateRequestDto dto : addCertificateRequestDtos) {
            Certificate certificate = CertificateConverter.toCertificate(dto, student);
            certificates.add(certificate);
        }
        return certificateRepository.saveAll(certificates);
    }
    @Transactional
    public List<Certificate> updateCertificates(List<UpdateCertificateRequestDto> updateCertificateRequestDtos, Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        List<Certificate> certificates = new ArrayList<>();
        for (UpdateCertificateRequestDto dto : updateCertificateRequestDtos) {
            Certificate certificate = certificateRepository.findByIdAndStudent(dto.getCertificateId(), student)
                    .orElseThrow(() -> new IllegalArgumentException("해당 자격증이 존재하지 않습니다."));
            certificate.update(dto.getName(), dto.getDate());
            certificates.add(certificate);
        }
        return certificates;
    }

    @Transactional(readOnly = true)
    public List<Certificate> viewCertificate(Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        return certificateRepository.findAllByStudent_id(stuId);
    }

    @Transactional
    public void deleteCertificate(Long stuId, Long certificateId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번이 존재하지 않습니다. id=" + stuId));
        certificateRepository.deleteByIdAndStudent(certificateId, student);
    }
}
