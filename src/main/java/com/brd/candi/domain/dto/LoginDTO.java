package com.brd.candi.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
public class LoginDTO {
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
