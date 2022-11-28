package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class VagaModel {
    private UUID id;
    private String nome;
    private String tipo;
    private String sobre;
    private String experiencia;
    private String beneficios;
    private LocalDate dataTermino;
    private EmpresaModel empresa;
    private List<String> perguntas;
}
