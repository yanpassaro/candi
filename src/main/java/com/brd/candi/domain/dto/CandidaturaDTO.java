package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Resposta;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CandidaturaDTO {
    @NotNull
    private UUID vaga;
    private List<String> respostas;

    public List<Resposta> getRespostas() {
        return respostas.stream().map(resposta ->
                Resposta.builder().respostaC(resposta).build()
        ).distinct().toList();
    }
}
