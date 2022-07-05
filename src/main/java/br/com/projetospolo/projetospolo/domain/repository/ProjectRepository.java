package br.com.projetospolo.projetospolo.domain.repository;

import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.domain.model.Project;
import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByIdAndOwner_Id(Long id, Long ownerId);

    @Query("select proj from Project proj " +
        "WHERE ((:name Is NULL) OR (proj.name LIKE '%:name%')) " +
        "AND ((:projectSituation Is NULL) OR (proj.projectSituation = :projectSituation)) " +
        "AND ((:startDate Is NULL) OR (proj.startDate >= :startDate)) " +
        "AND ((:endDate Is NULL) OR (proj.startDate <= :endDate)) "
    )
    Page<Project> filter(
        @Param(value = "name") String name,
        @Param(value = "projectSituation") String projectSituation,
        @Param(value = "startDate") Date startDate,
        @Param(value = "endDate") Date endDate,
        Pageable pageable
    );
}
