package com.brd.candi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class EnderecoDTO {
    private UUID id;
    @Size(max = 30)
    private String cidade;
    @Size(max = 30)
    private String estado;
}
