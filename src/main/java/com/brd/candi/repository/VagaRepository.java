package com.brd.candi.repository;

import com.brd.candi.model.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, UUID> {
}
