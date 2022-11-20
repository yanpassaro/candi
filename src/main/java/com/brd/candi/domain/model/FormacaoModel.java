package com.brd.candi.domain.model;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public class FormacaoModel {
    private String instituto;
    private String curso;
    private String sobre;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataTermino;
}
