package com.brd.candi.domain.model;

import lombok.Builder;

@Builder
public class EnderecoModel {
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
}
