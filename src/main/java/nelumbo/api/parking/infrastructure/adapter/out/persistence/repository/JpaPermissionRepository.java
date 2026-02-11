package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
}
