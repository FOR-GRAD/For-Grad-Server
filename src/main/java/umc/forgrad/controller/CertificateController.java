package umc.forgrad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.dto.AddCertificateRequestDto;
import umc.forgrad.dto.AddCertificateResponseDto;
import umc.forgrad.dto.ViewCertificateResponseDto;
import umc.forgrad.service.CertificateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping(value = "/plans/certifications/stuId/{stuId}")
    public ApiResponse<AddCertificateResponseDto> addCertificate(@RequestBody AddCertificateRequestDto addCertificateRequestDto, @PathVariable Long stuId) {
        Certificate certificate = certificateService.addCertificate(addCertificateRequestDto, stuId);
        return ApiResponse.onSuccess(CertificateConverter.toAddResultDto(certificate));
    }
    @GetMapping(value = "/plans/certifications/stuId/{stuId}")
    public ApiResponse<List<ViewCertificateResponseDto>> viewCertificate(@PathVariable Long stuId) {
        List<Certificate> certificates= certificateService.viewCertificate(stuId);
        return ApiResponse.onSuccess(CertificateConverter.toViewResultDto(certificates));
    }
}
