package com.brd.candi.repository;

import com.brd.candi.domain.entity.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {
    boolean existsVagaByNomeAndEmpresaRecrutadoresId(String nome, UUID id);

    boolean existsVagaByEmpresaRecrutadoresId(UUID id);

    Vaga findVagaById(UUID id);

    Page<Vaga> findAllByEmpresaId(UUID id, Pageable page);
}
