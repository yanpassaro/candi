package com.brd.candi.repository;

import com.brd.candi.model.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
}
