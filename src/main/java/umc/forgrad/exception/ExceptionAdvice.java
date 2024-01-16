package umc.forgrad.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.forgrad.apipayload.ApiResponse;
import umc.forgrad.apipayload.code.ReasonDto;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    protected ApiResponse<ReasonDto> handleGeneralException(GeneralException exception) {
        ReasonDto reasonDto = exception.getErrorStatus().getReason();
        return ApiResponse.onFailure(reasonDto.getCode(), reasonDto.getMessage(), null);
    }

}
