package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Empresa;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class VagaDTO {
    private UUID id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 20)
    private String nome;
    private String tipo;
    private String experiencia;
    private String beneficios;
    private String sobre;
    @NotNull
    private Empresa empresa;
}
