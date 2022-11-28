package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
import com.brd.candi.domain.entity.empresa.Admin;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EmpresaDTO {
    private UUID id;
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String nome;
    @Size(min = 14, max = 14)
    private String cnpj;
    @Size(max = 300)
    private String sobre;
    private ContatoDTO contato;
    private EnderecoDTO endereco;
    @NotNull
    private Set<AdminDTO> administradores;

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
                .telefone(contato.getTelefone())
                .email(contato.getEmail())
                .blog(contato.getSite())
                .build();
    }

    public List<Admin> getAdmins() {
        return administradores.stream().map(admin ->
                Admin.builder()
                        .id(admin.getId())
                        .nome(admin.getNome())
                        .sobrenome(admin.getSobrenome())
                        .email(admin.getEmail())
                        .senha(admin.getSenha())
                        .sobre(admin.getSobre())
                        .role(admin.getRole().getRoleNome())
                        .build()
        ).distinct().limit(10).toList();
    }
}