package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Role;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RolePersistenceMapper {

    private final PermissionPersistenceMapper permissionMapper;

    public Role toDomain(RoleEntity entity) {
        if (entity == null)
            return null;
        return new Role(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPermissions() != null
                        ? entity.getPermissions().stream().map(permissionMapper::toDomain).collect(Collectors.toList())
                        : null);
    }

    public RoleEntity toEntity(Role domain) {
        if (domain == null)
            return null;
        return new RoleEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPermissions() != null
                        ? domain.getPermissions().stream().map(permissionMapper::toEntity).collect(Collectors.toList())
                        : null);
    }
}
