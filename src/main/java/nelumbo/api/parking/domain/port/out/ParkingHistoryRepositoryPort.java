package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.ParkingHistory;

public interface ParkingHistoryRepositoryPort {
    ParkingHistory save(ParkingHistory history);

    boolean existsByPlateAndParkingId(String plate, Long parkingId);
}
