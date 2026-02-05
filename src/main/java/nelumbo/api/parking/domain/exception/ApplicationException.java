package nelumbo.api.parking.domain.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCodes error;

    public ApplicationException(ErrorCodes error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApplicationException(ErrorCodes error, String customMessage) {
        super(customMessage);
        this.error = error;
    }
}
