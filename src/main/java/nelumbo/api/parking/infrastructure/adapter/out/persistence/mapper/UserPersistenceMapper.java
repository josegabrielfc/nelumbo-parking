package nelumbo.api.parking.infrastructure.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceMapper {

    private final RolePersistenceMapper roleMapper;

    public User toDomain(UserEntity entity) {
        if (entity == null)
            return null;
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                roleMapper.toDomain(entity.getRole()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getToken());
    }

    public UserEntity toEntity(User domain) {
        if (domain == null)
            return null;
        return new UserEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPassword(),
                roleMapper.toEntity(domain.getRole()),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getToken());
    }
}
