package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.domain.model.VehicleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaVehicleRecordRepository extends JpaRepository<VehicleRecord, Long> {
    Optional<VehicleRecord> findByPlate(String plate);

    Optional<VehicleRecord> findByPlateAndParkingId(String plate, Long parkingId);

    List<VehicleRecord> findByParkingId(Long parkingId);

    long countByParkingId(Long parkingId);

    boolean existsByPlate(String plate);
}
