package nelumbo.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
                @NotBlank(message = "El email es obligatorio") @Email(message = "El email debe tener un formato válido") String email,
                @NotBlank(message = "La placa es obligatoria") String placa,
                @NotBlank(message = "El mensaje es obligatorio") String mensaje,
                @NotBlank(message = "El nombre del parqueadero es obligatorio") String parqueaderoNombre) {
}
