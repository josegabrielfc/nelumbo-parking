package nelumbo.api.parking.infrastructure.adapter.in.web.mapper;

import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public User toDomain(UserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return user;
    }
}
