package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.dto.Certificate.*;
import umc.forgrad.service.CertificateService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping(value = "/plans/certifications")
    public ApiResponse<List<AddCertificateResponseDto>> addCertificate(@RequestBody List<AddCertificateRequestDto> addCertificateRequestDtos, @SessionAttribute(name="student") Long stuId) {
        List<Certificate> certificates = certificateService.addCertificates(addCertificateRequestDtos, stuId);
        List<AddCertificateResponseDto> addCertificateResponseDtos = new ArrayList<>();
        for (Certificate certificate: certificates) {
            AddCertificateResponseDto addCertificateResponseDto = CertificateConverter.toAddResultDto(certificate);
            addCertificateResponseDtos.add(addCertificateResponseDto);
        }
        return ApiResponse.onSuccess(addCertificateResponseDtos);
    }
    @PatchMapping(value = "/plans/certifications")
    public ApiResponse<List<UpdateCertificateResponseDto>> updateCertificate(@RequestBody List<UpdateCertificateRequestDto> updateCertificateRequestDtos, @SessionAttribute(name="student") Long stuId) {
        List<Certificate> certificates = certificateService.updateCertificates(updateCertificateRequestDtos, stuId);
        List<UpdateCertificateResponseDto> updateCertificateResponseDtos = new ArrayList<>();
        for(Certificate certificate: certificates) {
            UpdateCertificateResponseDto updateCertificateResponseDto = CertificateConverter.toUpdateResultDto(certificate);
            updateCertificateResponseDtos.add(updateCertificateResponseDto);
        }
        return ApiResponse.onSuccess(updateCertificateResponseDtos);
    }
    @GetMapping(value = "/plans/certifications")
    public ApiResponse<List<ViewCertificateResponseDto>> viewCertificate(@SessionAttribute(name="student") Long stuId) {
        List<Certificate> certificates= certificateService.viewCertificate(stuId);
        return ApiResponse.onSuccess(CertificateConverter.toViewResultDto(certificates));
    }

    @DeleteMapping(value = "/plans/certifications")
    public ApiResponse<List<DeleteCertificateResponseDto>> deleteCertificate(@SessionAttribute(name="student") Long stuId, @RequestParam Long certificateId) {
        List<Certificate> certificates = certificateService.deletCertificate(stuId);
        return ApiResponse.onSuccess(CertificateConverter.toDeleteResultDto(certificates));
    }
}
