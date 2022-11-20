package com.brd.candi.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public class UsuarioModel {
    private UUID id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String imagemUrl;
}
