package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.ParkingEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingPersistenceMapper {

    private final UserPersistenceMapper userMapper;

    public Parking toDomain(ParkingEntity entity) {
        if (entity == null)
            return null;
        return new Parking(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getCostPerHour(),
                userMapper.toDomain(entity.getSocio()),
                entity.getDeletedAt());
    }

    public ParkingEntity toEntity(Parking domain) {
        if (domain == null)
            return null;
        return new ParkingEntity(
                domain.getId(),
                domain.getName(),
                domain.getCapacity(),
                domain.getCostPerHour(),
                userMapper.toEntity(domain.getSocio()),
                domain.getDeletedAt());
    }
}
