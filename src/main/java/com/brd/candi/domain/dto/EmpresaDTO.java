package com.brd.candi.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
public class EmpresaDTO {
    private UUID id;
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String nome;
    @Size(min = 14, max = 14)
    private String cnpj;
    @Size(max = 300)
    private String sobre;
    private String imagemUrl;
    @NotNull
    private UsuarioDTO administrador;
    @NotNull
    private Set<UsuarioDTO> administradores;
}