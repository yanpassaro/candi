package com.brd.candi.repository;

import com.brd.candi.domain.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
}
