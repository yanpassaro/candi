package com.brd.candi.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
    private String destino;
    private String assunto;
    private String conteudo;
}
