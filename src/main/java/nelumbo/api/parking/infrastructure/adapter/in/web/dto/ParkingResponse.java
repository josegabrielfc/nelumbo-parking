package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

public record ParkingResponse(
        Long id,
        String name,
        Integer capacity,
        Double costPerHour,
        Long socioId,
        String socioName) {
}
