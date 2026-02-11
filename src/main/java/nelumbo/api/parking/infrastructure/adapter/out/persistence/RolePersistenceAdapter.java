package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Role;
import nelumbo.api.parking.domain.port.out.RoleRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.RolePersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository jpaRoleRepository;
    private final RolePersistenceMapper mapper;

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public Role save(Role role) {
        return mapper.toDomain(jpaRoleRepository.save(mapper.toEntity(role)));
    }
}
