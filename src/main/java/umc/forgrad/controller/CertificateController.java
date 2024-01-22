package umc.forgrad.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.converter.CertificateConverter;
import umc.forgrad.domain.Certificate;
import umc.forgrad.dto.CertificateRequestDto;
import umc.forgrad.dto.CertificateResponseDto;
import umc.forgrad.service.CertificateService;

@RestController
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping(value = "/plans/certifications/stuId/{stuId}")
    public ApiResponse<CertificateResponseDto> addCertificate(@RequestBody @Valid CertificateRequestDto certificateRequestDto) {
        Certificate certificate = certificateService.addCertificate(certificateRequestDto);
        return ApiResponse.onSuccess(CertificateConverter.toAddResultDto(certificate));
    }


}
