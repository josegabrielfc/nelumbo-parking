package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.VehicleRecord;

public interface CheckInUseCase {
    VehicleRecord registerEntry(String plate, Long parkingId);
}
