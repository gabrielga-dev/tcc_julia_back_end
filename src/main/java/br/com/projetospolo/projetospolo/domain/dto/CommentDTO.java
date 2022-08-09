package br.com.projetospolo.projetospolo.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CommentDTO {

    private Long id;
    private String title;
    private String content;
    private Date creationDate;
    private UserDTO author;
}
