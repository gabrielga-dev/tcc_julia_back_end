package br.com.projetospolo.projetospolo.domain.repository;

import br.com.projetospolo.projetospolo.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT user from User user WHERE user.id NOT IN (:ids) AND user.active = 1 ")
    Page<User> getUsersNotIn(@Param("ids") List<Long> ids, Pageable pageable);

    @Query("select user from User user " +
        "WHERE ((:id Is NULL) OR (user.id = :id)) " +
        "AND ((:firstName Is NULL) OR (user.firstName LIKE '%:firstName%')) " +
        "AND ((:lastName Is NULL) OR (user.lastName LIKE '%:lastName%')) " +
        "AND ((:email Is NULL) OR (user.email LIKE '%:email%')) " +
        "AND ((:roleId Is NULL) OR (:roleId IN (SELECT role.id FROM user.roles role))) " +
        "AND user.active = 1 "
    )
    Page<User> filter(
        @Param(value = "id") Long id,
        @Param(value = "firstName") String firstName,
        @Param(value = "lastName") String lastName,
        @Param(value = "email") String email,
        @Param(value = "roleId") Long roleId,
        Pageable pageable
    );
}
