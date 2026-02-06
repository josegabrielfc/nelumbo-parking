package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.model.indicator.ParkingFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEarnings;
import nelumbo.api.parking.domain.model.indicator.VehicleFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEntries;
import nelumbo.api.parking.domain.model.indicator.ParkingEarnings;
import nelumbo.api.parking.domain.port.out.ParkingHistoryRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaParkingHistoryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
        return jpaParkingHistoryRepository.existsByPlateAndParkingId(plate, parkingId);
    }

    @Override
    public List<VehicleFrequency> findTop10FrequentVehicles() {
        return jpaParkingHistoryRepository.findTop10FrequentVehicles();
    }

    @Override
    public List<SocioEarnings> findTop3SociosByEarnings() {
        return jpaParkingHistoryRepository.findTop3SociosByEarnings();
    }

    @Override
    public List<ParkingFrequency> findTop10FrequentParkings() {
        return jpaParkingHistoryRepository.findTop10FrequentParkings();
    }

    @Override
    public List<SocioEntries> findTop3SociosByEntriesBetween(LocalDateTime start, LocalDateTime end) {
        return jpaParkingHistoryRepository.findTop3SociosByEntriesBetween(start, end);
    }

    @Override
    public List<ParkingEarnings> findTop3ParkingsByEarningsBetween(LocalDateTime start, LocalDateTime end) {
        return jpaParkingHistoryRepository.findTop3ParkingsByEarningsBetween(start, end);
    }

    @Override
    public Double sumEarningsBySocioIdAndPeriod(Long socioId, LocalDateTime start, LocalDateTime end) {
        Double sum = jpaParkingHistoryRepository.sumEarningsBySocioIdAndPeriod(socioId, start, end);
        return sum != null ? sum : 0.0;
    }

    @Override
    public Double sumEarningsByParkingIdAndPeriod(Long parkingId, LocalDateTime start, LocalDateTime end) {
        Double sum = jpaParkingHistoryRepository.sumEarningsByParkingIdAndPeriod(parkingId, start, end);
        return sum != null ? sum : 0.0;
    }
}
