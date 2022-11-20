package com.brd.candi.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class EnderecoDTO {
    private UUID id;
    @Size(min = 14, max = 14)
    private String cep;
    @Size(max = 30)
    private String cidade;
    @Size(max = 30)
    private String estado;
    @Size(max = 30)
    private String pais;
}
