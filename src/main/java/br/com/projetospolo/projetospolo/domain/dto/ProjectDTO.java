package br.com.projetospolo.projetospolo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;

    private String name;

    private String description;

    private Integer maxParticipants;

    private String projectSituation;

    private Date startDate;

    private Date endDate;

    private UserDTO owner;

    private Set<UserDTO> participants;

    private List<CommentDTO> comments;
}
