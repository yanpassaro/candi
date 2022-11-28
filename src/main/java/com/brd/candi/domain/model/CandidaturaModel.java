package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CandidaturaModel {
    private UUID id;
    private LocalDate dataEnvio;
    private VagaModel vaga;
    private String status;
    private UsuarioModel candidato;
    private List<String> perguntas;
    private List<String> respostas;
}
