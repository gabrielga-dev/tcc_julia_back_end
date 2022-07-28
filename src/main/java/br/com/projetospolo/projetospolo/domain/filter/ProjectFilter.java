package br.com.projetospolo.projetospolo.domain.filter;

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

    private Long id;

    private String name;

    private String projectSituation;

    private Date startDate;

    private Date endDate;
}
