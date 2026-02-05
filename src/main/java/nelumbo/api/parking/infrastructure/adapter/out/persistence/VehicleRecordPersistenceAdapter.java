package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaVehicleRecordRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehicleRecordPersistenceAdapter implements VehicleRecordRepositoryPort {

    private final JpaVehicleRecordRepository jpaVehicleRecordRepository;

    @Override
    public VehicleRecord save(VehicleRecord record) {
        return jpaVehicleRecordRepository.save(record);
    }

    @Override
    public Optional<VehicleRecord> findByPlate(String plate) {
        return jpaVehicleRecordRepository.findByPlate(plate);
    }

    @Override
    public Optional<VehicleRecord> findByPlateAndParkingId(String plate, Long parkingId) {
        return jpaVehicleRecordRepository.findByPlateAndParkingId(plate, parkingId);
    }

    @Override
    public List<VehicleRecord> findByParkingId(Long parkingId) {
        return jpaVehicleRecordRepository.findByParkingId(parkingId);
    }

    @Override
    public long countByParkingId(Long parkingId) {
        return jpaVehicleRecordRepository.countByParkingId(parkingId);
    }

    @Override
    public void deleteById(Long id) {
        jpaVehicleRecordRepository.deleteById(id);
    }

    @Override
    public boolean existsByPlate(String plate) {
        return jpaVehicleRecordRepository.existsByPlate(plate);
    }
}
