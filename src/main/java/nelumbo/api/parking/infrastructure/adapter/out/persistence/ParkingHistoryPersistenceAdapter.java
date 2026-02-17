package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.model.indicator.*;
import nelumbo.api.parking.domain.port.out.ParkingHistoryRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.ParkingHistoryPersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaParkingHistoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ParkingHistoryPersistenceAdapter implements ParkingHistoryRepositoryPort {

    private final JpaParkingHistoryRepository jpaParkingHistoryRepository;
    private final ParkingHistoryPersistenceMapper mapper;

    @Override
    public ParkingHistory save(ParkingHistory history) {
        return mapper.toDomain(jpaParkingHistoryRepository.save(mapper.toEntity(history)));
    }

    @Override
    public boolean existsByPlateAndParkingId(String plate, Long parkingId) {
        return jpaParkingHistoryRepository.existsByPlateAndParkingId(plate, parkingId);
    }

    @Override
    public List<VehicleFrequency> findTop10FrequentVehicles() {
        return jpaParkingHistoryRepository.findTopFrequentVehicles(PageRequest.of(0, 10));
    }

    @Override
    public List<SocioEarnings> findTop3SociosByEarnings() {
        return jpaParkingHistoryRepository.findTopSociosByEarnings(PageRequest.of(0, 3));
    }

    @Override
    public List<ParkingFrequency> findTop10FrequentParkings() {
        return jpaParkingHistoryRepository.findTopFrequentParkings(PageRequest.of(0, 10));
    }

    @Override
    public List<SocioEntries> findTop3SociosByEntriesBetween(LocalDateTime start, LocalDateTime end) {
        return jpaParkingHistoryRepository.findTopSociosByEntriesBetween(start, end, PageRequest.of(0, 3));
    }

    @Override
    public List<ParkingEarnings> findTop3ParkingsByEarningsBetween(LocalDateTime start, LocalDateTime end) {
        return jpaParkingHistoryRepository.findTopParkingsByEarningsBetween(start, end, PageRequest.of(0, 3));
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

    @Override
    @Transactional
    public void deleteByParkingId(Long parkingId) {
        jpaParkingHistoryRepository.deleteByParkingId(parkingId);
    }
}
