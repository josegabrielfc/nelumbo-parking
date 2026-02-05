package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,

        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") String email,

        @NotBlank(message = "El password es obligatorio") String password) {
}
