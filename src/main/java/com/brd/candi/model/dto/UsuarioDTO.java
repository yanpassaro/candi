package com.brd.candi.model.dto;

import lombok.Data;

import javax.validation.constraints.*;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
public class UsuarioDTO {
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
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 20)
    private String nome;
    @Size(max = 20)
    private String sobrenome;
    private String role;

    public String getSenha() {
        return encodeToString(encodeToString(senha.getBytes()).getBytes());
    }
}
