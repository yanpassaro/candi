package com.brd.candi.repository;

import com.brd.candi.model.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@EnableJpaRepositories
public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
}
