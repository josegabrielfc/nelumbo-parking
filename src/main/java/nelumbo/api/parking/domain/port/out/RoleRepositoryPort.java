package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.Role;
import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);
}