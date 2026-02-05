package nelumbo.api.parking.domain.port.in;

import nelumbo.api.parking.infrastructure.adapter.in.web.dto.LoginRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.AuthResponse;

public interface AuthUseCase {
    AuthResponse login(LoginRequest loginRequest);

    void logout(String email);
}
