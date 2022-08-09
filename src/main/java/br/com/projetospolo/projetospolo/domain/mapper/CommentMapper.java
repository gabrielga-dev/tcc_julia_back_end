package br.com.projetospolo.projetospolo.domain.mapper;

import br.com.projetospolo.projetospolo.domain.dto.CommentDTO;
import br.com.projetospolo.projetospolo.domain.form.CommentForm;
import br.com.projetospolo.projetospolo.domain.model.Comment;
import br.com.projetospolo.projetospolo.infrastructure.mapper.DefaultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper implements DefaultMapper<CommentForm, CommentDTO, Comment> {

    private final UserMapper userMapper;

    @Override
    public Comment domainFromForm(CommentForm form) {
        var toReturn = new Comment();
        toReturn.setTitle(form.getTitle());
        toReturn.setContent(form.getContent());
        return toReturn;
    }

    @Override
    public CommentDTO dtoFromDomain(Comment comment) {
        return CommentDTO.builder()
            .id(comment.getId())
            .title(comment.getTitle())
            .content(comment.getContent())
            .creationDate(comment.getCreationDate())
            .author(userMapper.dtoFromDomain(comment.getAuthor()))
            .build();
    }

    @Override
    public Comment transferInformation(Comment source, Comment destiny) {
        return null;
    }
}
