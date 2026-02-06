package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.domain.model.indicator.ParkingFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEarnings;
import nelumbo.api.parking.domain.model.indicator.VehicleFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEntries;
import nelumbo.api.parking.domain.model.indicator.ParkingEarnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {

        @Query("SELECT new nelumbo.api.parking.domain.model.indicator.VehicleFrequency(h.plate, COUNT(h)) " +
                        "FROM ParkingHistory h " +
                        "GROUP BY h.plate " +
                        "ORDER BY COUNT(h) DESC, h.plate ASC " +
                        "LIMIT 10")
        List<VehicleFrequency> findTop10FrequentVehicles();

        @Query("SELECT new nelumbo.api.parking.domain.model.indicator.SocioEarnings(p.socio.id, p.socio.name, SUM(h.totalCost)) "
                        +
                        "FROM ParkingHistory h JOIN h.parking p " +
                        "GROUP BY p.socio.id, p.socio.name " +
                        "ORDER BY SUM(h.totalCost) DESC " +
                        "LIMIT 3")
        List<SocioEarnings> findTop3SociosByEarnings();

        @Query("SELECT new nelumbo.api.parking.domain.model.indicator.ParkingFrequency(p.id, p.name, COUNT(h)) " +
                        "FROM ParkingHistory h JOIN h.parking p " +
                        "GROUP BY p.id, p.name " +
                        "ORDER BY COUNT(h) DESC, p.id ASC " +
                        "LIMIT 10")
        List<ParkingFrequency> findTop10FrequentParkings();

        @Query("SELECT SUM(h.totalCost) FROM ParkingHistory h JOIN h.parking p " +
                        "WHERE p.socio.id = :socioId AND h.exitDate BETWEEN :start AND :end")
        Double sumEarningsBySocioIdAndPeriod(@Param("socioId") Long socioId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT SUM(h.totalCost) FROM ParkingHistory h " +
                        "WHERE h.parking.id = :parkingId AND h.exitDate BETWEEN :start AND :end")
        Double sumEarningsByParkingIdAndPeriod(@Param("parkingId") Long parkingId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT new nelumbo.api.parking.domain.model.indicator.SocioEntries(p.socio.name, COUNT(h)) " +
                        "FROM ParkingHistory h JOIN h.parking p " +
                        "WHERE h.entryDate BETWEEN :start AND :end " +
                        "GROUP BY p.socio.id, p.socio.name " +
                        "ORDER BY COUNT(h) DESC " +
                        "LIMIT 3")
        List<SocioEntries> findTop3SociosByEntriesBetween(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT new nelumbo.api.parking.domain.model.indicator.ParkingEarnings(p.name, SUM(h.totalCost)) " +
                        "FROM ParkingHistory h JOIN h.parking p " +
                        "WHERE h.exitDate BETWEEN :start AND :end " +
                        "GROUP BY p.id, p.name " +
                        "ORDER BY SUM(h.totalCost) DESC " +
                        "LIMIT 3")
        List<ParkingEarnings> findTop3ParkingsByEarningsBetween(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        boolean existsByPlateAndParkingId(String plate, Long parkingId);
}
