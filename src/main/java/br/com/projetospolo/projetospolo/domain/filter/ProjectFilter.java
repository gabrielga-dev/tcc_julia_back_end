package br.com.projetospolo.projetospolo.domain.filter;

import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {

    private String name;

    private ProjectSituationType projectSituation;

    private Date startDate;

    private Date endDate;
}
