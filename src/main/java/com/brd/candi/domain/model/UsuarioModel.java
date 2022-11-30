package com.brd.candi.domain.model;

import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UsuarioModel {
    private UUID id;
    private String nome;
    private String sobrenome;
    private String sobre;
    private Endereco endereco;
    private Contato contato;
}
