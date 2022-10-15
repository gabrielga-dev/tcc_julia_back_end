package br.com.projetospolo.projetospolo.domain.repository;

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
        "WHERE ((:id Is NULL) OR (proj.id = :id)) " +
        "AND ((:name Is NULL) OR (proj.name LIKE '%:name%')) " +
        "AND ((:projectSituation Is NULL) OR (proj.projectSituation = :projectSituation)) " +
        "AND ((:startDate Is NULL) OR (proj.startDate >= :startDate)) " +
        "AND ((:endDate Is NULL) OR (proj.startDate <= :endDate)) "
    )
    Page<Project> filter(
        @Param(value = "id") Long id,
        @Param(value = "name") String name,
        @Param(value = "projectSituation") ProjectSituationType projectSituation,
        @Param(value = "startDate") Date startDate,
        @Param(value = "endDate") Date endDate,
        Pageable pageable
    );

    @Query("SELECT project FROM Project project " +
        "WHERE :participantId IN (SELECT participant.id FROM project.participants participant) " +
        "AND (SELECT COUNT(comment.id) FROM Comment comment WHERE comment.project.id = project.id AND comment.author.id = :participantId) = 0")
    Page<Project> findProjectsWithoutComments(
        @Param("participantId") Long participantId,
        Pageable pageable
    );

    @Query("SELECT project FROM Project project " +
        "WHERE project.owner.id = :ownerId " +
        "AND (SELECT COUNT(comment.id) FROM project.comments comment) > 0")
    Page<Project> findCommentedProjects(
        @Param("ownerId") Long ownerId,
        Pageable pageable
    );
}
