package umc.forgrad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.domain.Student;
import umc.forgrad.dto.Certificate.AddCertificateRequestDto;
import umc.forgrad.dto.Certificate.UpdateCertificateRequestDto;
import umc.forgrad.exception.GeneralException;
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
                .orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
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
                .orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        List<Certificate> certificates = new ArrayList<>();
        for (UpdateCertificateRequestDto dto : updateCertificateRequestDtos) {
            Certificate certificate = certificateRepository.findByIdAndStudent(dto.getCertificateId(), student)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.CERTIFICATE_NOT_FOUND));
            certificate.update(dto.getName(), dto.getDate());
            certificates.add(certificate);
        }
        return certificates;
    }

    @Transactional(readOnly = true)
    public List<Certificate> viewCertificate(Long stuId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        return certificateRepository.findAllByStudent_id(stuId);
    }

    @Transactional
    public void deleteCertificate(Long stuId, Long certificateId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STUDENT_NOT_FOUND));
        certificateRepository.deleteByIdAndStudent(certificateId, student);
    }
}
