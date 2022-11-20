package com.brd.candi.repository;

import com.brd.candi.model.entity.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, UUID> {
}
