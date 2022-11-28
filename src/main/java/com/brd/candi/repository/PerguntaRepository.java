package com.brd.candi.repository;

import com.brd.candi.domain.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerguntaRepository extends JpaRepository<Pergunta, UUID> {

}