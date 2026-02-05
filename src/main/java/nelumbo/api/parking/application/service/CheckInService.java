package nelumbo.api.parking.application.service;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.CheckInUseCase;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInService implements CheckInUseCase {

    private final VehicleRecordRepositoryPort vehicleRecordRepositoryPort;
    private final ParkingRepositoryPort parkingRepositoryPort;

    @Override
    public VehicleRecord registerEntry(String plate, Long parkingId) {
        validatePlate(plate);

        if (vehicleRecordRepositoryPort.existsByPlate(plate)) {
            throw new ApplicationException(ErrorCodes.PLATE_IN_SYSTEM);
        }
        Parking parking = parkingRepositoryPort.findById(parkingId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.PARKING_NOT_FOUND));

        long currentVehicles = vehicleRecordRepositoryPort.countByParkingId(parkingId);
        if (currentVehicles >= parking.getCapacity()) {
            throw new ApplicationException(ErrorCodes.PARKING_FULL);
        }

        VehicleRecord record = new VehicleRecord();
        record.setPlate(plate.toUpperCase());
        record.setEntryDate(LocalDateTime.now());
        record.setParking(parking);

        return vehicleRecordRepositoryPort.save(record);
    }

    private void validatePlate(String plate) {
        if (plate == null || plate.length() != 6 || !plate.matches("^[a-zA-Z0-9]+$")
                || plate.toLowerCase().contains("ñ")) {
            throw new ApplicationException(ErrorCodes.INVALID_PLATE);
        }
    }
}
