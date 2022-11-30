package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Resposta;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CandidaturaDTO {
    private List<String> respostas;

    public List<Resposta> getRespostas() {
        return respostas.stream().map(resposta ->
                Resposta.builder().respostaC(resposta).build()
        ).distinct().toList();
    }
}
