package br.com.projetospolo.projetospolo.domain.repository;

import br.com.projetospolo.projetospolo.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
