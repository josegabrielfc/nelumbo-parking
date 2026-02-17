package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.VehicleRecordEntity;

public interface JpaVehicleRecordRepository extends JpaRepository<VehicleRecordEntity, Long> {
    Optional<VehicleRecordEntity> findByPlate(String plate);

    Optional<VehicleRecordEntity> findByPlateAndParkingId(String plate, Long parkingId);

    List<VehicleRecordEntity> findByParkingId(Long parkingId);

    long countByParkingId(Long parkingId);

    boolean existsByPlate(String plate);

    @Modifying
    @Query(value = "UPDATE vehicle_record SET deleted_at = CURRENT_TIMESTAMP WHERE parking_id = :parkingId", nativeQuery = true)
    void deleteByParkingId(@Param("parkingId") Long parkingId);
}
