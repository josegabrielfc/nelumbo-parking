package nelumbo.api.parking.application.usecase;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.model.indicator.*;
import nelumbo.api.parking.domain.port.in.IndicatorService;
import nelumbo.api.parking.domain.port.out.ParkingHistoryRepositoryPort;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicatorUseCase implements IndicatorService {

    private final ParkingHistoryRepositoryPort parkingHistoryRepositoryPort;
    private final VehicleRecordRepositoryPort vehicleRecordRepositoryPort;

    @Override
    public List<VehicleFrequency> getTop10FrequentVehicles() {
        return parkingHistoryRepositoryPort.findTop10FrequentVehicles();
    }

    @Override
    public List<SocioEarnings> getTop3SociosByEarnings() {
        return parkingHistoryRepositoryPort.findTop3SociosByEarnings();
    }

    @Override
    public List<ParkingFrequency> getTop10FrequentParkings() {
        return parkingHistoryRepositoryPort.findTop10FrequentParkings();
    }

    @Override
    public List<SocioEntries> getTop3SociosByWeeklyEntries() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        LocalDateTime end = now.with(LocalTime.MAX);
        return parkingHistoryRepositoryPort.findTop3SociosByEntriesBetween(start, end);
    }

    @Override
    public List<ParkingEarnings> getTop3ParkingsByWeeklyEarnings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        LocalDateTime end = now.with(LocalTime.MAX);
        return parkingHistoryRepositoryPort.findTop3ParkingsByEarningsBetween(start, end);
    }

    @Override
    public List<VehicleRecord> getFirstTimeVehicles(Long parkingId) {
        List<VehicleRecord> activeVehicles = vehicleRecordRepositoryPort.findByParkingId(parkingId);

        return activeVehicles.stream()
                .filter(v -> !parkingHistoryRepositoryPort.existsByPlateAndParkingId(v.getPlate(), parkingId))
                .collect(Collectors.toList());
    }

    @Override
    public Double getSocioEarnings(Long socioId, String period) {
        LocalDateTime[] range = calculateRange(period);
        return parkingHistoryRepositoryPort.sumEarningsBySocioIdAndPeriod(socioId, range[0], range[1]);
    }

    @Override
    public Double getParkingEarnings(Long parkingId, String period) {
        LocalDateTime[] range = calculateRange(period);
        return parkingHistoryRepositoryPort.sumEarningsByParkingIdAndPeriod(parkingId, range[0], range[1]);
    }

    private LocalDateTime[] calculateRange(String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end = now.with(LocalTime.MAX);

        switch (period.toLowerCase()) {
            case "today":
                start = now.with(LocalTime.MIN);
                break;
            case "week":
                start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
                break;
            case "month":
                start = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                break;
            case "year":
                start = now.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
                break;
            default:
                throw new ApplicationException(ErrorCodes.INVALID_INDICATOR_PERIOD);
        }
        return new LocalDateTime[] { start, end };
    }
}
