package com.sistemagestaousuariosback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemagestaousuariosback.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
