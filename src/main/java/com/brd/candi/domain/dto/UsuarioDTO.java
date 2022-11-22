package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Atividade;
import com.brd.candi.domain.entity.Endereco;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.*;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
@Builder
public class UsuarioDTO {
    private UUID id;
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
    private EnderecoDTO endereco;
    private Set<AtividadeDTO> atividades;

    public String getSenha() {
        return encodeToString(encodeToString(senha.getBytes()).getBytes());
    }

    public Endereco getEndereco() {
        return Endereco.builder()
                .id(endereco.getId())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .build();
    }

    public Set<Atividade> getAtividades(){
        Set<Atividade> atividadeList = new HashSet<>();
        for (AtividadeDTO atividadeDTO: atividades) {
            atividadeList.add(
                    Atividade.builder()
                            .id(atividadeDTO.getId())
                            .ativo(atividadeDTO.isAtivo())
                            .nome(atividadeDTO.getNome())
                            .local(atividadeDTO.getLocal())
                            .sobre(atividadeDTO.getSobre())
                            .dataInicio(atividadeDTO.getDataInicio())
                            .dataTermino(atividadeDTO.getDataTermino())
                            .build()
            );
        }
        return atividadeList;
    }
}
