package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailModel {
    private List<String> destino;
    private String assunto;
    private String texto;
}
