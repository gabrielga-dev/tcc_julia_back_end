package br.com.projetospolo.projetospolo.domain.repository;

import br.com.projetospolo.projetospolo.domain.model.Role;
import br.com.projetospolo.projetospolo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
