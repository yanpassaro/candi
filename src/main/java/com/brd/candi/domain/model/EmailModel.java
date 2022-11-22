package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailModel {
    private String destino;
    private String assunto;
    private String conteudo;
}
