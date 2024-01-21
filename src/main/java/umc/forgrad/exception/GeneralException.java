package umc.forgrad.exception;


import lombok.Getter;
import umc.forgrad.apipayload.code.ReasonDto;
import umc.forgrad.apipayload.code.status.ErrorStatus;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public GeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ReasonDto getErrorStatus() {
        return this.errorStatus.getReason();
    }

}
