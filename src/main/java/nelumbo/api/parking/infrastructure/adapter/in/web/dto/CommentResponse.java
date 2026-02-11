package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        boolean hasReplies,
        Long parentId) {
}
