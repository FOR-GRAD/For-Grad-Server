package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.dto.Certificate.AddCertificateRequestDto;
import umc.forgrad.dto.Certificate.AddCertificateResponseDto;
import umc.forgrad.dto.Certificate.ViewCertificateResponseDto;
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
    @GetMapping(value = "/plans/certifications")
    public ApiResponse<List<ViewCertificateResponseDto>> viewCertificate(@SessionAttribute(name="student") Long stuId) {
        List<Certificate> certificates= certificateService.viewCertificate(stuId);
        return ApiResponse.onSuccess(CertificateConverter.toViewResultDto(certificates));
    }
}
