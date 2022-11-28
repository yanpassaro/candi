package com.brd.candi.repository;

import com.brd.candi.domain.entity.Candidatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
    void deleteAllByVagaId(UUID id);

    boolean existsCandidaturaByIdAndCandidatoId(UUID id, UUID candidato);

    boolean existsCandidaturaByVagaIdAndCandidatoId(UUID vaga, UUID candidato);

    boolean existsCandidaturaByVagaEmpresaRecrutadoresId(UUID admin);

    boolean existsCandidaturaByIdAndVagaEmpresaRecrutadoresId(UUID id, UUID admin);

    boolean existsCandidaturaByCandidatoId(UUID candidato);

    Candidatura findCandidaturasById(UUID id);

    Page<Candidatura> findCandidaturasByVagaId(UUID vaga, Pageable pageable);

    Page<Candidatura> findCandidaturasByCandidatoId(UUID candidato, Pageable pageable);
}
