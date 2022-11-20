package com.brd.candi.repository;

import com.brd.candi.domain.entity.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FormacaoRepository extends JpaRepository<Formacao, UUID> {
}
