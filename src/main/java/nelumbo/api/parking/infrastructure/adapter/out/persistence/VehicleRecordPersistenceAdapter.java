package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.out.VehicleRecordRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.VehicleRecordPersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaVehicleRecordRepository;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleRecordPersistenceAdapter implements VehicleRecordRepositoryPort {

    private final JpaVehicleRecordRepository jpaVehicleRecordRepository;
    private final VehicleRecordPersistenceMapper mapper;

    @Override
    public VehicleRecord save(VehicleRecord record) {
        return mapper.toDomain(jpaVehicleRecordRepository.save(mapper.toEntity(record)));
    }

    @Override
    public Optional<VehicleRecord> findByPlate(String plate) {
        return jpaVehicleRecordRepository.findByPlate(plate).map(mapper::toDomain);
    }

    @Override
    public Optional<VehicleRecord> findByPlateAndParkingId(String plate, Long parkingId) {
        return jpaVehicleRecordRepository.findByPlateAndParkingId(plate, parkingId).map(mapper::toDomain);
    }

    @Override
    public List<VehicleRecord> findByParkingId(Long parkingId) {
        return jpaVehicleRecordRepository.findByParkingId(parkingId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
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

    @Override
    public List<VehicleRecord> findAll() {
        return jpaVehicleRecordRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByParkingId(Long parkingId) {
        jpaVehicleRecordRepository.deleteByParkingId(parkingId);
    }
}
