package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaParkingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParkingPersistenceAdapter implements ParkingRepositoryPort {

    private final JpaParkingRepository jpaParkingRepository;

    @Override
    public Parking save(Parking parking) {
        return jpaParkingRepository.save(parking);
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return jpaParkingRepository.findById(id);
    }

    @Override
    public List<Parking> findAll() {
        return jpaParkingRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaParkingRepository.deleteById(id);
    }

    @Override
    public List<Parking> findBySocioId(Long socioId) {
        return jpaParkingRepository.findBySocioId(socioId);
    }
}
