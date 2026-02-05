package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String roleName,
        LocalDateTime createdAt) {
}