package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.domain.model.User;

public interface CreateUserUseCase {
    User createSocio(User newUser);
}
