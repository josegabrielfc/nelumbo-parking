package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.VehicleRecord;
import java.util.List;
import java.util.Optional;

public interface VehicleRecordRepositoryPort {
    VehicleRecord save(VehicleRecord record);

    Optional<VehicleRecord> findByPlate(String plate);

    Optional<VehicleRecord> findByPlateAndParkingId(String plate, Long parkingId);

    List<VehicleRecord> findByParkingId(Long parkingId);

    long countByParkingId(Long parkingId);

    void deleteById(Long id);

    boolean existsByPlate(String plate);
}
