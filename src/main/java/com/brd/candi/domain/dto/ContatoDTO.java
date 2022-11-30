package com.brd.candi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class ContatoDTO {
    private UUID id;
    @Email
    @Size(min = 8, max = 30)
    private String email;
    @Size(min = 14, max = 14)
    private String telefone;
    @Size(max = 300)
    private String site;
}
