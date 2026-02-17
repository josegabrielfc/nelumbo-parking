package nelumbo.api.parking.application.usecase;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.SendEmailService;
import nelumbo.api.parking.domain.port.out.EmailServicePort;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendEmailUseCase implements SendEmailService {
    private final EmailServicePort emailServicePort;
    private final ParkingRepositoryPort parkingRepositoryPort;
    private final VehicleRecordRepositoryPort vehicleRecordRepositoryPort;

    @Override
    public String sendEmail(String email, String plate, String message, Long parkingId) {
        // 1. Validar que el parqueadero existe
        Parking parking = parkingRepositoryPort.findById(parkingId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.PARKING_NOT_FOUND));
        // 2. Validar que la placa se encuentra en el parqueadero
        Optional<VehicleRecord> vehicleRecord = vehicleRecordRepositoryPort
                .findByPlateAndParkingId(plate, parkingId);
        if (vehicleRecord.isEmpty()) {
            throw new ApplicationException(ErrorCodes.VEHICLE_NOT_IN_PARKING);
        }
        // 3. Enviar el correo al microservicio
        return emailServicePort.sendEmail(email, plate, message, parking.getName());
    }
}