package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.VehicleRecordEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleRecordPersistenceMapper {

    private final ParkingPersistenceMapper parkingMapper;

    public VehicleRecord toDomain(VehicleRecordEntity entity) {
        if (entity == null)
            return null;
        return new VehicleRecord(
                entity.getId(),
                entity.getPlate(),
                entity.getEntryDate(),
                parkingMapper.toDomain(entity.getParking()),
                entity.getDeletedAt());
    }

    public VehicleRecordEntity toEntity(VehicleRecord domain) {
        if (domain == null)
            return null;
        return new VehicleRecordEntity(
                domain.getId(),
                domain.getPlate(),
                domain.getEntryDate(),
                parkingMapper.toEntity(domain.getParking()),
                domain.getDeletedAt());
    }
}
