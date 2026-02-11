package nelumbo.api.parking.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import nelumbo.api.parking.domain.model.Comment;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {

    // Obtener comentarios raíz (parent_id is NULL) para un Post
    Page<Comment> findByPostIdAndParentIdIsNullOrderByCreatedAtDesc(Long postId, Pageable pageable);

    // Obtener respuestas de un comentario específico
    Page<Comment> findByParentIdOrderByCreatedAtDesc(Long parentId, Pageable pageable);

    // Contar hijos para saber si tiene respuestas (para el flag hasReplies)
    boolean existsByParentId(Long parentId);
}
