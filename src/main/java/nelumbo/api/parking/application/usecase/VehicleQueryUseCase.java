package nelumbo.api.parking.application.usecase;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.VehicleQueryService;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleQueryUseCase implements VehicleQueryService {

    private final VehicleRecordRepositoryPort vehicleRecordRepositoryPort;
    private final ParkingRepositoryPort parkingRepositoryPort;

    @Override
    public List<VehicleRecord> findActiveVehiclesByParking(Long parkingId) {
        parkingRepositoryPort.findById(parkingId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.PARKING_NOT_FOUND));

        return vehicleRecordRepositoryPort.findByParkingId(parkingId);
    }

    @Override
    public List<VehicleRecord> findAllActiveVehicles() {
        return vehicleRecordRepositoryPort.findAll();
    }
}
