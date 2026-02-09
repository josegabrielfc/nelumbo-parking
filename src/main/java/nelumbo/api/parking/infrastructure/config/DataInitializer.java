package nelumbo.api.parking.infrastructure.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.out.PasswordEncoderPort;
import nelumbo.api.parking.domain.port.out.RoleRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepositoryPort roleRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public void run(String... args) {
        // Solo nos encargamos de crear el Usuario Admin por defecto si no existe.
        // Los Roles y Permisos ya fueron creados por Flyway (V1__Initial_Setup.sql).

        if (userRepositoryPort.findByEmail("admin@mail.com").isEmpty()) {
            roleRepositoryPort.findByName("ADMIN").ifPresent(adminRole -> {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@mail.com");
                admin.setPassword(passwordEncoderPort.encode("admin"));
                admin.setRole(adminRole);
                admin.setStatus(1);
                admin.setCreatedAt(LocalDateTime.now());
                userRepositoryPort.save(admin);
                System.out.println("Default Admin user created: admin@mail.com / admin");
            });
        }
    }
}
