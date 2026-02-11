package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRoleId(Long roleId);
}
