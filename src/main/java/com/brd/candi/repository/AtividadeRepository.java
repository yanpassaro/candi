package com.brd.candi.repository;

import com.brd.candi.domain.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AtividadeRepository extends JpaRepository<Atividade, UUID> {
}
