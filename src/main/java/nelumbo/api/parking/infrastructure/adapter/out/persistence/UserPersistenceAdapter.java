package nelumbo.api.parking.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;
import nelumbo.api.parking.domain.port.out.UserSessionRepositoryPort;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort, UserSessionRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaUserRepository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(Long roleId) {
        return jpaUserRepository.findByRoleId(roleId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateToken(String email, String token) {
        jpaUserRepository.findByEmail(email).ifPresent(userEntity -> {
            userEntity.setToken(token);
            jpaUserRepository.save(userEntity);
        });
    }
}
