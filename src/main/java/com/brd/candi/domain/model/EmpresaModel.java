package com.brd.candi.domain.model;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public class EmpresaModel {
    private UUID id;
    private String nome;
    private String cnpj;
    private String sobre;
    private String imagemUrl;
    private UsuarioModel administrador;
    private Set<UsuarioModel> recrutadores;
}
