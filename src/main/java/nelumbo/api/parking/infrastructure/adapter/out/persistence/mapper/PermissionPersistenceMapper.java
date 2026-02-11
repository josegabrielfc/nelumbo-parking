package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import nelumbo.api.parking.domain.model.Permission;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionPersistenceMapper {

    public Permission toDomain(PermissionEntity entity) {
        if (entity == null)
            return null;
        return new Permission(
                entity.getId(),
                entity.getName(),
                entity.getDescription());
    }

    public PermissionEntity toEntity(Permission domain) {
        if (domain == null)
            return null;
        return new PermissionEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription());
    }
}
