package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserSessionRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort, UserSessionRepositoryPort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    @Override
    public List<User> findByRole(Long roleId) {
        return jpaUserRepository.findByRoleId(roleId);
    }

    @Override
    public void updateToken(String email, String token) {
        jpaUserRepository.findByEmail(email).ifPresent(user -> {
            user.setToken(token);
            jpaUserRepository.save(user);
        });
    }
}
