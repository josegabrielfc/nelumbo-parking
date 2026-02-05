package nelumbo.api.parking.application.service;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Role;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.CreateUserUseCase;
import nelumbo.api.parking.domain.port.out.PasswordEncoderPort;
import nelumbo.api.parking.domain.port.out.RoleRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserManagementService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public User createSocio(User newUser) {

        Role socioRole = roleRepositoryPort.findByName("SOCIO")
                .orElseThrow(() -> new RuntimeException("El rol SOCIO no existe en el sistema"));

        String hashedPassword = passwordEncoderPort.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setRole(socioRole);
        newUser.setStatus(1);
        newUser.setCreatedAt(LocalDateTime.now());

        return userRepositoryPort.save(newUser);
    }
}