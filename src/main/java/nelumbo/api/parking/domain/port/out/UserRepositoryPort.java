package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();

    List<User> findByRole(Long roleId);
}