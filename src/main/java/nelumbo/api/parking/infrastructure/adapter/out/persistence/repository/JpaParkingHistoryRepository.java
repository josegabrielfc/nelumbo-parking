package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.ParkingHistoryEntity;
import nelumbo.api.parking.domain.model.indicator.ParkingFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEarnings;
import nelumbo.api.parking.domain.model.indicator.VehicleFrequency;
import nelumbo.api.parking.domain.model.indicator.SocioEntries;
import nelumbo.api.parking.domain.model.indicator.ParkingEarnings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaParkingHistoryRepository extends JpaRepository<ParkingHistoryEntity, Long> {

        @Query("SELECT h.plate as plate, COUNT(h) as frequency " +
                        "FROM ParkingHistoryEntity h " +
                        "GROUP BY h.plate " +
                        "ORDER BY frequency DESC, h.plate ASC")
        List<VehicleFrequency> findTopFrequentVehicles(Pageable pageable);

        @Query("SELECT p.socio.id as socioId, p.socio.name as socioName, SUM(h.totalCost) as totalEarnings " +
                        "FROM ParkingHistoryEntity h JOIN h.parking p " +
                        "GROUP BY p.socio.id, p.socio.name " +
                        "ORDER BY totalEarnings DESC")
        List<SocioEarnings> findTopSociosByEarnings(Pageable pageable);

        @Query("SELECT p.id as parkingId, p.name as parkingName, COUNT(h) as vehicleCount " +
                        "FROM ParkingHistoryEntity h JOIN h.parking p " +
                        "GROUP BY p.id, p.name " +
                        "ORDER BY vehicleCount DESC, p.id ASC")
        List<ParkingFrequency> findTopFrequentParkings(Pageable pageable);

        @Query("SELECT SUM(h.totalCost) FROM ParkingHistoryEntity h JOIN h.parking p " +
                        "WHERE p.socio.id = :socioId AND h.exitDate BETWEEN :start AND :end")
        Double sumEarningsBySocioIdAndPeriod(@Param("socioId") Long socioId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT SUM(h.totalCost) FROM ParkingHistoryEntity h " +
                        "WHERE h.parking.id = :parkingId AND h.exitDate BETWEEN :start AND :end")
        Double sumEarningsByParkingIdAndPeriod(@Param("parkingId") Long parkingId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT p.socio.name as socioName, COUNT(h) as entryCount " +
                        "FROM ParkingHistoryEntity h JOIN h.parking p " +
                        "WHERE h.entryDate BETWEEN :start AND :end " +
                        "GROUP BY p.socio.id, p.socio.name " +
                        "ORDER BY entryCount DESC")
        List<SocioEntries> findTopSociosByEntriesBetween(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end, Pageable pageable);

        @Query("SELECT p.name as parkingName, SUM(h.totalCost) as totalEarnings " +
                        "FROM ParkingHistoryEntity h JOIN h.parking p " +
                        "WHERE h.exitDate BETWEEN :start AND :end " +
                        "GROUP BY p.id, p.name " +
                        "ORDER BY totalEarnings DESC")
        List<ParkingEarnings> findTopParkingsByEarningsBetween(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end, Pageable pageable);

        boolean existsByPlateAndParkingId(String plate, Long parkingId);
}
