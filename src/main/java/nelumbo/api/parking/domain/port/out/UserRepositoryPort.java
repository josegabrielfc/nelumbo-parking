package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
}