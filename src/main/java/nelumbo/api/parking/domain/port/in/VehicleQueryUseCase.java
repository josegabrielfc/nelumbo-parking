package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.VehicleRecord;
import java.util.List;

public interface VehicleQueryUseCase {
    List<VehicleRecord> findActiveVehiclesByParking(Long parkingId);
}
