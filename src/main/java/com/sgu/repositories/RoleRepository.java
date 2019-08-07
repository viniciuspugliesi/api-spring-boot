package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
