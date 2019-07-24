package com.sistemagestaousuariosback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemagestaousuariosback.domain.HttpLog;

@Repository
public interface HttpLogRepository extends JpaRepository<HttpLog, Integer> {
}
