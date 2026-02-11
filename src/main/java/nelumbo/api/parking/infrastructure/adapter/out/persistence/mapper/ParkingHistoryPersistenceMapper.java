package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.ParkingHistory;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.ParkingHistoryEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingHistoryPersistenceMapper {

    private final ParkingPersistenceMapper parkingMapper;

    public ParkingHistory toDomain(ParkingHistoryEntity entity) {
        if (entity == null)
            return null;
        return new ParkingHistory(
                entity.getId(),
                entity.getPlate(),
                entity.getEntryDate(),
                entity.getExitDate(),
                entity.getTotalCost(),
                parkingMapper.toDomain(entity.getParking()));
    }

    public ParkingHistoryEntity toEntity(ParkingHistory domain) {
        if (domain == null)
            return null;
        return new ParkingHistoryEntity(
                domain.getId(),
                domain.getPlate(),
                domain.getEntryDate(),
                domain.getExitDate(),
                domain.getTotalCost(),
                parkingMapper.toEntity(domain.getParking()));
    }
}
