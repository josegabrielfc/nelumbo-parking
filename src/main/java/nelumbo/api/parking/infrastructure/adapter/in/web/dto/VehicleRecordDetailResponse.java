package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record VehicleRecordDetailResponse(
        Long id,
        String plate,
        LocalDateTime entryDate,
        ParkingDetailResponse parking) {
    public record ParkingDetailResponse(
            Long id,
            String name,
            Integer capacity,
            Double costPerHour,
            SocioDetailResponse socio) {
    }

    public record SocioDetailResponse(
            Long socioId,
            String socioName) {
    }
}
