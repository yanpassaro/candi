package com.brd.candi.repository;

import com.brd.candi.domain.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {
}
