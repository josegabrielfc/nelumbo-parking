package nelumbo.api.parking.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {
        BAD_REQUEST("BAD_REQUEST", "La solicitud es inválida", 400),
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Ocurrió un error inesperado en el servidor", 500),
        INVALID_URL("INVALID_URL", "La URL solicitada no existe en este servidor", 404),
        INVALID_TOKEN("INVALID_TOKEN", "El token es inválido, ha expirado o no fue proporcionado", 401),
        WRONG_CREDENTIALS("WRONG_CREDENTIALS", "Credenciales inválidas", 400),
        MISSING_REQUEST_BODY("MISSING_REQUEST_BODY", "El cuerpo de la solicitud no es válido o está vacío", 400),
        ACCESS_DENIED("ACCESS_DENIED", "No tienes permisos suficientes para acceder a este recurso", 403),
        PARKING_NOT_FOUND("PARKING_NOT_FOUND", "Parqueadero no encontrado", 404),
        USER_NOT_FOUND("USER_NOT_FOUND", "Usuario no encontrado", 404),
        USER_NOT_SOCIO("USER_NOT_SOCIO", "El usuario asignado debe tener el rol SOCIO", 404),
        PLATE_IN_SYSTEM("PLATE_IN_SYSTEM",
                        "No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero", 400),
        PARKING_FULL("PARKING_FULL",
                        "No se puede registrar el ingreso, el parqueadero ha alcanzado su capacidad máxima", 400),
        PLATE_NOT_IN_PARKING("PLATE_NOT_IN_PARKING",
                        "No se puede Registrar Salida, no existe la placa en el parqueadero", 400),
        INVALID_PLATE("INVALID_PLATE",
                        "La placa del vehículo debe tener 6 caracteres alfanuméricos (sin caracteres especiales ni la letra ñ)",
                        400),
        INVALID_INDICATOR_PERIOD("INVALID_INDICATOR_PERIOD", "Periodo de indicaciones inválido", 400),
        EMAIL_SERVICE_ERROR("EMAIL_SERVICE_ERROR", "Error al comunicarse con el servicio de correo", 500),
        VEHICLE_NOT_IN_PARKING("VEHICLE_NOT_IN_PARKING",
                        "El vehículo con la placa especificada no se encuentra en el parqueadero", 404),
        MISSING_SOCIO_ID("MISSING_SOCIO_ID", "Se debe proporcionar el ID del socio", 400),
        PARKING_NOT_OWNED("PARKING_NOT_OWNED", "No se puede realizar la acción, el parqueadero no pertenece al socio",
                        403),
        PARKING_HAS_VEHICLES("PARKING_HAS_VEHICLES",
                        "No se puede eliminar el parqueadero porque tiene vehículos activos",
                        400);

        private final String code;
        private final String message;
        private final int httpStatus;

        ErrorCodes(String code, String message, int httpStatus) {
                this.code = code;
                this.message = message;
                this.httpStatus = httpStatus;
        }
}
