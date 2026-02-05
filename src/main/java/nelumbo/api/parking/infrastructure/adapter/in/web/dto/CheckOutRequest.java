package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckOutRequest(
        @NotBlank(message = "La placa es obligatoria") String plate,

        @NotNull(message = "El ID del parqueadero es obligatorio") Long parkingId) {
}
