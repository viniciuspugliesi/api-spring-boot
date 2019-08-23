package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
}
