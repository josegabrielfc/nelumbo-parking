package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Comment;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CommentResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CommentTreeResponse;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaCommentRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final JpaCommentRepository commentRepository;

    // 1. Obtener comentarios raíz de un post (Paginado)
    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getRootComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Comment> comments = commentRepository.findByPostIdAndParentIdIsNullOrderByCreatedAtDesc(
                postId, PageRequest.of(page, size));

        return ResponseEntity.ok(comments.map(this::mapToResponse));
    }

    // 2. Obtener respuestas de un comentario (Lazy Loading / Paginado)
    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<Page<CommentResponse>> getReplies(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Comment> replies = commentRepository.findByParentIdOrderByCreatedAtDesc(
                commentId, PageRequest.of(page, size));

        return ResponseEntity.ok(replies.map(this::mapToResponse));
    }

    // 3. Obtener TODO el árbol de un comentario (N-ario) con límite de profundidad
    // y paginación por nivel
    @GetMapping("/comments/{commentId}/tree")
    public ResponseEntity<CommentTreeResponse> getCommentTree(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "3") int maxDepth,
            @RequestParam(defaultValue = "5") int childrenSize) {
        return commentRepository.findById(commentId)
                .map(comment -> ResponseEntity
                        .ok(mapToTreeResponse(comment, 1, maxDepth, childrenSize)))
                .orElse(ResponseEntity.notFound().build());
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                commentRepository.existsByParentId(comment.getId()), // Verifica si tiene hijos
                comment.getParent() != null ? comment.getParent().getId() : null);
    }

    // Método recursivo para construir el árbol con límite de profundidad y anchura
    private CommentTreeResponse mapToTreeResponse(Comment comment, int currentDepth, int maxDepth,
            int childrenSize) {
        boolean hasMore = false;
        List<CommentTreeResponse> children = List.of();

        if (currentDepth < maxDepth) {
            // Usamos el repositorio para traer solo la primera "página" de hijos para este
            // nodo
            Page<Comment> childrenPage = commentRepository.findByParentIdOrderByCreatedAtDesc(
                    comment.getId(), PageRequest.of(0, childrenSize));

            hasMore = childrenPage.hasNext();
            children = childrenPage.getContent().stream()
                    .map(child -> mapToTreeResponse(child, currentDepth + 1, maxDepth,
                            childrenSize)) // Llamada
                                           // recursiva
                    .toList();
        } else {
            // Si llegamos al límite de profundidad, solo verificamos si existen hijos para
            // avisar al Front
            hasMore = commentRepository.existsByParentId(comment.getId());
        }

        return new CommentTreeResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                hasMore,
                children);
    }

}
