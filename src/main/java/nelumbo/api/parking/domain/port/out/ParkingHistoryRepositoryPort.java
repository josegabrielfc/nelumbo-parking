package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.model.indicator.ParkingFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEarnings;
import nelumbo.api.parking.domain.model.indicator.VehicleFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEntries;
import nelumbo.api.parking.domain.model.indicator.ParkingEarnings;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingHistoryRepositoryPort {
    ParkingHistory save(ParkingHistory history);

    boolean existsByPlateAndParkingId(String plate, Long parkingId);

    // Admin Indicators
    List<VehicleFrequency> findTop10FrequentVehicles();

    List<SocioEarnings> findTop3SociosByEarnings();

    List<ParkingFrequency> findTop10FrequentParkings();

    List<SocioEntries> findTop3SociosByEntriesBetween(LocalDateTime start, LocalDateTime end);

    List<ParkingEarnings> findTop3ParkingsByEarningsBetween(LocalDateTime start, LocalDateTime end);

    // Socio Indicators
    Double sumEarningsBySocioIdAndPeriod(Long socioId, LocalDateTime start, LocalDateTime end);

    Double sumEarningsByParkingIdAndPeriod(Long parkingId, LocalDateTime start, LocalDateTime end);

    void deleteByParkingId(Long parkingId);
}
