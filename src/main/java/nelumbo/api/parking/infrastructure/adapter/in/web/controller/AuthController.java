package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.port.in.AuthUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.AuthResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.LoginRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authUseCase.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(Authentication authentication) {
        authUseCase.logout(authentication.getName());
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
