package com.brd.candi.domain.dto;

import com.brd.candi.domain.enumaration.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AnaliseDTO {
    @NotNull
    private UUID candidatura;
    @NotNull
    private Status status;
}