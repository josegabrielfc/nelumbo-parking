package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.Permission;
import java.util.Optional;

public interface PermissionRepositoryPort {
    Optional<Permission> findByName(String name);

    Permission save(Permission permission);
}
