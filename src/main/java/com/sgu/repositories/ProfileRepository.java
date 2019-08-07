package com.sgu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgu.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}