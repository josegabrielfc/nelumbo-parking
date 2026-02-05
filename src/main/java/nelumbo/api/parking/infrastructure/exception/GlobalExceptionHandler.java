package nelumbo.api.parking.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApplicationException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", ex.getError().getCode());
        response.put("message", ex.getMessage());

        return ResponseEntity.status(ex.getError().getHttpStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", ErrorCodes.ACCESS_DENIED.getCode());
        response.put("message", ErrorCodes.ACCESS_DENIED.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoResourceFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", "INVALID_URL");
        response.put("message", "La URL solicitada no existe en este servidor.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", "INTERNAL_SERVER_ERROR");
        response.put("message", "Ocurrió un error inesperado. Por favor contacta al soporte.");

        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        String paramName = ex.getName();
        String paramValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";

        response.put("code", "INVALID_PARAMETER_TYPE");
        response.put("message",
                String.format("El parámetro '%s' con valor '%s' no es válido. Se esperaba un tipo '%s'.",
                        paramName, paramValue, requiredType));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", ErrorCodes.MISSING_REQUEST_BODY.getCode());
        response.put("message", ErrorCodes.MISSING_REQUEST_BODY.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
