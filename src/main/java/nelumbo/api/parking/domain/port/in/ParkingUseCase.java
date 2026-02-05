package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.Parking;
import java.util.List;

public interface ParkingUseCase {
    Parking create(Parking parking);

    Parking update(Long id, Parking parking);

    void delete(Long id);

    List<Parking> findAll();

    Parking findById(Long id);

    List<Parking> findBySocioId(Long socioId);
}
