package com.brd.candi.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class UsuarioDTO {
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 30)
    private String email;
    @Size(max = 20)
    private String senha;
}
