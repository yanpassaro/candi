package com.brd.candi.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CandidaturaDTO {
    @NotNull
    private UUID vaga;
    @NotNull
    private UUID candidato;
}