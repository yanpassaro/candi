package com.brd.candi.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class FormacaoDTO {
    private UUID id;
    @NotNull
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String instituto;
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String curso;
    @Size(max = 300)
    private String sobre;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataTermino;
}
