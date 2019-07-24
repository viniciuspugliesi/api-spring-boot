package com.sistemagestaousuariosback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemagestaousuariosback.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
