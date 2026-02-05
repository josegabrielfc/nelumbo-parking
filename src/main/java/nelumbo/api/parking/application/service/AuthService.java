package nelumbo.api.parking.application.service;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.AuthUseCase;
import nelumbo.api.parking.domain.port.out.TokenProviderPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserSessionRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.AuthResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.LoginRequest;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenProviderPort tokenProviderPort;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRepositoryPort userSessionRepository;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepositoryPort.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ApplicationException(ErrorCodes.WRONG_CREDENTIALS));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ApplicationException(ErrorCodes.WRONG_CREDENTIALS);
        }

        String token = tokenProviderPort.generateToken(user);
        userSessionRepository.updateToken(user.getEmail(), token);

        return new AuthResponse(token);
    }

    @Override
    public void logout(String email) {
        userSessionRepository.updateToken(email, null);
    }
}
