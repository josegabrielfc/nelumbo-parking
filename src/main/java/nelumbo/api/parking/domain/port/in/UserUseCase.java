package nelumbo.api.parking.domain.port.in;

import java.util.List;

import nelumbo.api.parking.domain.model.User;

public interface UserUseCase {

    List<User> findAll();

    User findById(Long id);

    List<User> findByRole(Long roleId);

    User createSocio(User newUser);
}
