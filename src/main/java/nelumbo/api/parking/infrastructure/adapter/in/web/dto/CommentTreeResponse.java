package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CommentTreeResponse(
		Long id,
		String content,
		LocalDateTime createdAt,
		boolean hasMoreReplies,
		List<CommentTreeResponse> replies) {
}
