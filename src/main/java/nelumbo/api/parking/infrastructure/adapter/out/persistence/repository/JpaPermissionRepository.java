package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}
