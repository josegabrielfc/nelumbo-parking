package nelumbo.api.parking.application.service;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.ParkingService;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingUseCase implements ParkingService {

    private final ParkingRepositoryPort parkingRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Parking create(Parking parking) {
        User socio = validateSocio(parking.getSocio().getId());
        parking.setSocio(socio);
        return parkingRepositoryPort.save(parking);
    }

    @Override
    public Parking update(Long id, Parking parking) {
        Parking existingParking = findById(id);
        User socio = validateSocio(parking.getSocio().getId());

        existingParking.setName(parking.getName());
        existingParking.setCapacity(parking.getCapacity());
        existingParking.setCostPerHour(parking.getCostPerHour());
        existingParking.setSocio(socio);

        return parkingRepositoryPort.save(existingParking);
    }

    @Override
    public void delete(Long id) {
        findById(id); // Validar que existe
        parkingRepositoryPort.deleteById(id);
    }

    @Override
    public List<Parking> findAll() {
        return parkingRepositoryPort.findAll();
    }

    @Override
    public Parking findById(Long id) {
        return parkingRepositoryPort.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.PARKING_NOT_FOUND));
    }

    @Override
    public List<Parking> findBySocioId(Long socioId, User requester) {
        Long targetSocioId = resolveSocioId(socioId, requester);
        return parkingRepositoryPort.findBySocioId(targetSocioId);
    }

    private Long resolveSocioId(Long requestedId, User requester) {
        boolean isAdmin = "ADMIN".equals(requester.getRole().getName());

        if (isAdmin) {
            if (requestedId == null) {
                throw new ApplicationException(ErrorCodes.MISSING_SOCIO_ID);
            }
            return requestedId;
        }

        // ES SOCIO
        if (requestedId != null) {
            throw new ApplicationException(ErrorCodes.ACCESS_DENIED);
        }

        return requester.getId();
    }

    private User validateSocio(Long socioId) {
        User user = userRepositoryPort.findById(socioId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.USER_NOT_FOUND));

        if (!"SOCIO".equals(user.getRole().getName())) {
            throw new ApplicationException(ErrorCodes.USER_NOT_SOCIO);
        }
        return user;
    }
}
