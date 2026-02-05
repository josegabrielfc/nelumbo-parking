package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record ActiveVehicleResponse(
        Long id,
        String placa,
        LocalDateTime fechaIngreso,
        String parqueadero) {
}
