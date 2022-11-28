package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmpresaModel {
    private UUID id;
    private String nome;
    private String cnpj;
    private String sobre;
    private String email;
    private String telefone;
    private String site;
    private String estado;
    private String cidade;
}
