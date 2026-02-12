package nelumbo.api.parking.application.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.CheckOutService;
import nelumbo.api.parking.domain.port.out.ParkingHistoryRepositoryPort;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;

@Service
@RequiredArgsConstructor
public class CheckOutUseCase implements CheckOutService {

    private final VehicleRecordRepositoryPort vehicleRecordRepositoryPort;
    private final ParkingHistoryRepositoryPort parkingHistoryRepositoryPort;
    private final OwnershipValidator ownershipValidator;

    @Override
    @Transactional
    public void registerExit(String plate, Long parkingId, User requester) {
        VehicleRecord record = vehicleRecordRepositoryPort.findByPlateAndParkingId(plate.toUpperCase(), parkingId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.PLATE_NOT_IN_PARKING));

        ownershipValidator.validate(record.getParking(), requester);

        LocalDateTime exitDate = LocalDateTime.now();

        Double totalCost = calculateCost(record.getEntryDate(), exitDate, record.getParking().getCostPerHour());

        ParkingHistory history = new ParkingHistory();
        history.setPlate(record.getPlate());
        history.setEntryDate(record.getEntryDate());
        history.setExitDate(exitDate);
        history.setTotalCost(totalCost);
        history.setParking(record.getParking());

        parkingHistoryRepositoryPort.save(history);

        vehicleRecordRepositoryPort.deleteById(record.getId());
    }

    private Double calculateCost(LocalDateTime entry, LocalDateTime exit, Double costPerHour) {
        Duration duration = Duration.between(entry, exit);
        long minutes = duration.toMinutes();
        double hours = minutes / 60.0;

        return Math.max(1, hours) * costPerHour;
    }
}
