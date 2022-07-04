package br.com.projetospolo.projetospolo.domain.repository;

import br.com.projetospolo.projetospolo.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
