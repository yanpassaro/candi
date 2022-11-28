package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Pergunta;
import com.brd.candi.domain.enumaration.Tipo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class VagaDTO {
    private UUID id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 20)
    private String nome;
    private Tipo tipo;
    private String experiencia;
    private String beneficios;
    private LocalDate dataTermino;
    private String sobre;
    private List<String> perguntas;

    public List<Pergunta> getPerguntas() {
        return perguntas.stream().map(pergunta ->
                Pergunta.builder()
                        .perguntaV(pergunta)
                        .build()
        ).toList();
    }
}
