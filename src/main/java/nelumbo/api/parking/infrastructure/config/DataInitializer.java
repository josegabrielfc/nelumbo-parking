package nelumbo.api.parking.infrastructure.config;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Permission;
import nelumbo.api.parking.domain.model.Role;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.out.PermissionRepositoryPort;
import nelumbo.api.parking.domain.port.out.RoleRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import nelumbo.api.parking.domain.port.out.PasswordEncoderPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepositoryPort permissionRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public void run(String... args) {
        // 1. Crear Permisos si no existen
        Permission createSocio = getOrCreatePermission("CREATE_SOCIO", "Crear usuarios con rol Socio");
        Permission manageParking = getOrCreatePermission("MANAGE_PARKING", "CRUD de parqueaderos");
        Permission viewVehicles = getOrCreatePermission("VIEW_VEHICLES", "Ver listado/detalle de vehículos");
        Permission sendEmails = getOrCreatePermission("SEND_EMAILS", "Enviar emails a los socios");
        Permission viewIndicators = getOrCreatePermission("VIEW_INDICATORS", "Ver indicadores");
        Permission registerEntry = getOrCreatePermission("REGISTER_ENTRY", "Registrar entrada de vehículos");
        Permission registerExit = getOrCreatePermission("REGISTER_EXIT", "Registrar salida de vehículos");
        Permission viewMyParking = getOrCreatePermission("VIEW_MY_PARKING", "Ver parqueaderos asociados");

        // 2. Crear Roles y asignar permisos
        Role adminRole = getOrCreateRole("ADMIN", "Administrador del sistema");
        List<Permission> adminPermissions = new ArrayList<>();
        adminPermissions.add(createSocio);
        adminPermissions.add(manageParking);
        adminPermissions.add(viewVehicles);
        adminPermissions.add(sendEmails);
        adminPermissions.add(viewIndicators);
        adminRole.setPermissions(adminPermissions);
        roleRepositoryPort.save(adminRole);

        Role socioRole = getOrCreateRole("SOCIO", "Usuario socio");
        List<Permission> socioPermissions = new ArrayList<>();
        socioPermissions.add(viewVehicles);
        socioPermissions.add(viewIndicators);
        socioPermissions.add(registerEntry);
        socioPermissions.add(registerExit);
        socioPermissions.add(viewMyParking);
        socioRole.setPermissions(socioPermissions);
        roleRepositoryPort.save(socioRole);

        // 3. Crear Usuario Admin por defecto
        if (userRepositoryPort.findByEmail("admin@mail.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@mail.com");
            admin.setPassword(passwordEncoderPort.encode("admin"));
            admin.setRole(adminRole);
            admin.setStatus(1);
            admin.setCreatedAt(LocalDateTime.now());
            userRepositoryPort.save(admin);
            System.out.println("Default Admin user created: admin@mail.com / admin");
        }
    }

    private Permission getOrCreatePermission(String name, String description) {
        return permissionRepositoryPort.findByName(name)
                .orElseGet(() -> {
                    Permission p = new Permission();
                    p.setName(name);
                    p.setDescription(description);
                    return permissionRepositoryPort.save(p);
                });
    }

    private Role getOrCreateRole(String name, String description) {
        return roleRepositoryPort.findByName(name)
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName(name);
                    r.setDescription(description);
                    return roleRepositoryPort.save(r);
                });
    }
}
