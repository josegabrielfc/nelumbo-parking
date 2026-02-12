package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.model.VehicleRecord;

public interface CheckInService {
    VehicleRecord registerEntry(String plate, Long parkingId, User requester);
}
