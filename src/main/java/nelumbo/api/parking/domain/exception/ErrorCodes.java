package nelumbo.api.parking.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    INVALID_TOKEN("INVALID_TOKEN", "El token es inválido, ha expirado o no fue proporcionado", 401),
    WRONG_CREDENTIALS("WRONG_CREDENTIALS", "Credenciales inválidas", 400),
    MISSING_REQUEST_BODY("MISSING_REQUEST_BODY", "El cuerpo de la solicitud no es válido o está vacío", 400),
    ACCESS_DENIED("ACCESS_DENIED", "No tienes permisos suficientes para acceder a este recurso", 403);

    private final String code;
    private final String message;
    private final int httpStatus;

    ErrorCodes(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
