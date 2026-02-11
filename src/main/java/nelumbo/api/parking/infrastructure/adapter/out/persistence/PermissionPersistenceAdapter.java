package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Permission;
import nelumbo.api.parking.domain.port.out.PermissionRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.PermissionPersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaPermissionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermissionPersistenceAdapter implements PermissionRepositoryPort {

    private final JpaPermissionRepository jpaPermissionRepository;
    private final PermissionPersistenceMapper mapper;

    @Override
    public Optional<Permission> findByName(String name) {
        return jpaPermissionRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public Permission save(Permission permission) {
        return mapper.toDomain(jpaPermissionRepository.save(mapper.toEntity(permission)));
    }
}
