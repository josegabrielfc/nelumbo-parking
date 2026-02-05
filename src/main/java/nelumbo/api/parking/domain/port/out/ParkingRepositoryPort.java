package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.Parking;
import java.util.List;
import java.util.Optional;

public interface ParkingRepositoryPort {
    Parking save(Parking parking);

    Optional<Parking> findById(Long id);

    List<Parking> findAll();

    void deleteById(Long id);

    List<Parking> findBySocioId(Long socioId);
}
