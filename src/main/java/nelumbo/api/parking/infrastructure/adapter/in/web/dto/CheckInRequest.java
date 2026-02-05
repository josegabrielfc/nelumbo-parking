package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CheckInRequest(
        @NotBlank(message = "La placa es obligatoria") @Size(min = 6, max = 6, message = "La placa debe tener 6 caracteres") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La placa solo puede contener caracteres alfanuméricos") String plate,

        @NotNull(message = "El ID del parqueadero es obligatorio") Long parkingId) {
}
