package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.HttpLog;

@Repository
public interface HttpLogRepository extends JpaRepository<HttpLog, Integer> {
}
