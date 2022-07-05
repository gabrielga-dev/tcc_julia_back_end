package br.com.projetospolo.projetospolo.domain.form;

import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "O atributo nome não pode ser nulo.")
    @Size(min = 5, max = 100, message = "O campo de nome deve ter de 5 até 30 caracteres.")
    private String name;

    @Size(max = 1000, message = "O campo de descrição deve ter de até 1000 caracteres.")
    private String description;

    @NotNull(message = "O número máximo de participantes não pode ser nulo.")
    @Positive(message ="O número máximo de participantes não pode ser zero ou negativo.")
    private Integer maxParticipants;

    @NotNull(message = "A situação do projeto não pode ser nula.")
    private ProjectSituationType projectSituation;

    @NotNull(message = "A data do início do projeto não pode ser nula.")
    private Date startDate;

    private Date endDate;
}
