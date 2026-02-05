package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkingRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,

        @NotNull(message = "La capacidad es obligatoria") @Min(value = 1, message = "La capacidad debe ser al menos 1") Integer capacity,

        @NotNull(message = "El costo por hora es obligatorio") @Min(value = 0, message = "El costo por hora no puede ser negativo") Double costPerHour,

        @NotNull(message = "El ID del socio es obligatorio") Long socioId) {
}
