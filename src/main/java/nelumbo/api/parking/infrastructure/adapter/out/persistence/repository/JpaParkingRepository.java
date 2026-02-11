package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaParkingRepository extends JpaRepository<ParkingEntity, Long> {
    List<ParkingEntity> findBySocioId(Long socioId);
}
