package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Atividade;
import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.Base64Utils.encodeToString;

@Data
@Builder
public class AtualizarUsuarioDTO {
    @NotNull
    private UUID id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 30)
    private String senha;
    @Size(max = 20)
    private String sobrenome;
    private ContatoDTO contato;
    private EnderecoDTO endereco;
    private List<AtividadeDTO> atividades;

    public String getSenha() {
        return encodeToString(encodeToString(senha.getBytes()).getBytes());
    }

    public Endereco getEndereco() {
        return Endereco.builder()
                .id(endereco.getId())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .build();
    }

    public Contato getContato() {
        return Contato.builder()
                .id(contato.getId())
                .email(contato.getEmail())
                .blog(contato.getSite())
                .telefone(contato.getTelefone())
                .portfolio(contato.getPortfolio())
                .build();
    }

    public List<Atividade> getAtividades() {
        return atividades.stream().map(atividade ->
                Atividade.builder()
                        .id(atividade.getId())
                        .local(atividade.getLocal())
                        .nome(atividade.getNome())
                        .dataInicio(atividade.getDataInicio())
                        .dataTermino(atividade.getDataTermino())
                        .sobre(atividade.getSobre())
                        .build()
        ).distinct().toList();
    }

}
