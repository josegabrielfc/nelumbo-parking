package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.VehicleRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaVehicleRecordRepository extends JpaRepository<VehicleRecordEntity, Long> {
    Optional<VehicleRecordEntity> findByPlate(String plate);

    Optional<VehicleRecordEntity> findByPlateAndParkingId(String plate, Long parkingId);

    List<VehicleRecordEntity> findByParkingId(Long parkingId);

    long countByParkingId(Long parkingId);

    boolean existsByPlate(String plate);
}
