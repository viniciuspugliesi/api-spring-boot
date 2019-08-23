package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.UserPolicies;

@Repository
public interface UserPoliciesRepository extends JpaRepository<UserPolicies, Integer> {
}
