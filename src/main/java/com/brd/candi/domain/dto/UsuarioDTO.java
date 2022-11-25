package com.brd.candi.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
@Builder
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
    private ContatoDTO contato;
    private EnderecoDTO endereco;
    private Set<AtividadeDTO> atividades;

    public String getSenha() {
        return encodeToString(encodeToString(senha.getBytes()).getBytes());
    }
}
