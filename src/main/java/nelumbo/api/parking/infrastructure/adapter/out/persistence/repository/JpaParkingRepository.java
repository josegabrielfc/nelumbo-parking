package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import nelumbo.api.parking.domain.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaParkingRepository extends JpaRepository<Parking, Long> {
    List<Parking> findBySocioId(Long socioId);
}
