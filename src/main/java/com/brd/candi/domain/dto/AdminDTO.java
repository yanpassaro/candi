package com.brd.candi.domain.dto;

import com.brd.candi.domain.enumaration.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.UUID;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
@Builder
public class AdminDTO {
    private UUID id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 20)
    private String nome;
    @Size(max = 20)
    private String sobrenome;
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 30)
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 30)
    private String senha;

    public String getSenha() {
        return encodeToString(encodeToString(senha.getBytes()).getBytes());
    }
}
