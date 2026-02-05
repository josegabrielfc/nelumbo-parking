package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.port.out.ParkingHistoryRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaParkingHistoryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingHistoryPersistenceAdapter implements ParkingHistoryRepositoryPort {

    private final JpaParkingHistoryRepository jpaParkingHistoryRepository;

    @Override
    public ParkingHistory save(ParkingHistory history) {
        return jpaParkingHistoryRepository.save(history);
    }

    @Override
    public boolean existsByPlateAndParkingId(String plate, Long parkingId) {
        return false;
    }
}
