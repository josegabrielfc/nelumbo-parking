package nelumbo.api.parking.infrastructure.adapter.in.web.mapper;

import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getName() : null,
                user.getCreatedAt());
    }
}
