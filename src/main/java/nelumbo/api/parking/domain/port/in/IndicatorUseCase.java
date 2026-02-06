package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.model.indicator.*;

import java.util.List;

public interface IndicatorUseCase {
    // Admin
    List<VehicleFrequency> getTop10FrequentVehicles();

    List<SocioEarnings> getTop3SociosByEarnings();

    List<ParkingFrequency> getTop10FrequentParkings();

    List<SocioEntries> getTop3SociosByWeeklyEntries();

    List<ParkingEarnings> getTop3ParkingsByWeeklyEarnings();

    // Socio
    List<VehicleRecord> getFirstTimeVehicles(Long parkingId);

    Double getSocioEarnings(Long socioId, String period);

    Double getParkingEarnings(Long parkingId, String period); // hoy, semana, mes, año
}
