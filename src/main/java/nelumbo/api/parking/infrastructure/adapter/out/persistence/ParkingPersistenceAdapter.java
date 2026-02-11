package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.port.out.ParkingRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.ParkingPersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaParkingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ParkingPersistenceAdapter implements ParkingRepositoryPort {

    private final JpaParkingRepository jpaParkingRepository;
    private final ParkingPersistenceMapper mapper;

    @Override
    public Parking save(Parking parking) {
        return mapper.toDomain(jpaParkingRepository.save(mapper.toEntity(parking)));
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return jpaParkingRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Parking> findAll() {
        return jpaParkingRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaParkingRepository.deleteById(id);
    }

    @Override
    public List<Parking> findBySocioId(Long socioId) {
        return jpaParkingRepository.findBySocioId(socioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
