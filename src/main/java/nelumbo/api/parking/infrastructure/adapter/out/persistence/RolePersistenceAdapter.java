package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Role;
import nelumbo.api.parking.domain.port.out.RoleRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name);
    }
}
