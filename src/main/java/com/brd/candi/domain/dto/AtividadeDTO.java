package com.brd.candi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class AtividadeDTO {
    private UUID id;
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String nome;
    @NotNull
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String local;
    @Size(max = 300)
    private String sobre;
    @NotNull
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private boolean ativo;
}
