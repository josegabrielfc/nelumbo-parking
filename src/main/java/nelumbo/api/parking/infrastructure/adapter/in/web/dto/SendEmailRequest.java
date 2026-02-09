package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendEmailRequest(
        @NotBlank(message = "El email es obligatorio") @Email(message = "El email debe tener un formato válido") String email,
        @NotBlank(message = "La placa es obligatoria") String placa,
        @NotBlank(message = "El mensaje es obligatorio") String mensaje,
        @NotNull(message = "El ID del parqueadero es obligatorio") Long parqueaderoId) {
}
