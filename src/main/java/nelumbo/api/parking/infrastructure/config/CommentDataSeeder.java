package nelumbo.api.parking.infrastructure.config;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Comment;
import nelumbo.api.parking.domain.model.Post;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaCommentRepository;
import nelumbo.api.parking.infrastructure.adapter.out.persistence.repository.JpaPostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDataSeeder implements CommandLineRunner {

    private final JpaPostRepository postRepository;
    private final JpaCommentRepository commentRepository;

    @Override
    public void run(String... args) {
        // 1. Crear un post de prueba si no hay ninguno
        if (postRepository.count() == 0) {
            Post post = new Post();
            post.setTitle("Prueba de Escalabilidad");
            post.setContent("Este post tendrá 1000 comentarios anidados.");
            post = postRepository.save(post);

            System.out.println("Iniciando inserción masiva de 1000 comentarios...");

            List<Comment> rootComments = new ArrayList<>();

            // 2. Insertar 1000 comentarios raíz
            for (int i = 1; i <= 1000; i++) {
                Comment comment = new Comment();
                comment.setPost(post);
                comment.setContent("Comentario raíz #" + i);
                comment.setCreatedAt(LocalDateTime.now().minusMinutes(i)); // Fechas distintas para probar orden
                rootComments.add(comment);
            }
            commentRepository.saveAll(rootComments);

            // 3. Insertar algunas respuestas en el primer comentario para probar
            // anidamiento
            Comment firstComment = rootComments.get(0);
            List<Comment> replies = new ArrayList<>();
            for (int j = 1; j <= 50; j++) {
                Comment reply = new Comment();
                reply.setPost(post);
                reply.setParent(firstComment);
                reply.setContent("Respuesta #" + j + " al comentario 1");
                reply.setCreatedAt(LocalDateTime.now().plusSeconds(j));
                replies.add(reply);
            }
            commentRepository.saveAll(replies);

            System.out.println("✅ Inserción completada con éxito.");
        }
    }
}