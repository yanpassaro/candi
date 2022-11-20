package com.brd.candi.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AnaliseDTO {
    @NotNull
    private UUID id;
    @NotNull
    private UUID candidatura;
    @NotNull
    private UUID candidato;
}