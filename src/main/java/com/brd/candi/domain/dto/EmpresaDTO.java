package com.brd.candi.domain.dto;

import com.brd.candi.domain.entity.Atividade;
import com.brd.candi.domain.entity.Endereco;
import com.brd.candi.domain.entity.Usuario;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.brd.candi.domain.enumaration.Role.OWNER;
import static com.brd.candi.domain.enumaration.Role.RECRUTADOR;
import static org.springframework.util.Base64Utils.encodeToString;

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
    private Endereco endereco;
    @NotNull
    private UsuarioDTO administrador;
    private Set<UsuarioDTO> administradores;

    public Endereco getEndereco() {
        return Endereco.builder()
                .id(endereco.getId())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .build();
    }

    public Usuario getAdministrador() {
        return Usuario.builder()
                .id(administrador.getId())
                .nome(administrador.getNome())
                .sobrenome(administrador.getSobrenome())
                .email(administrador.getEmail())
                .senha(administrador.getSenha())
                .role(OWNER.getRoleNome())
                .imagemUrl("/img/profile/" + encodeToString(administrador.getEmail().getBytes()))
                .build();
    }

    public Set<Usuario> getAdministradores() {
        Set<Usuario> administradoresList = new HashSet<>();
        for (UsuarioDTO usuarioDTO : administradores) {
            administradoresList.add(
                    Usuario.builder()
                            .id(usuarioDTO.getId())
                            .nome(usuarioDTO.getNome())
                            .sobrenome(usuarioDTO.getSobrenome())
                            .email(usuarioDTO.getEmail())
                            .senha(usuarioDTO.getSenha())
                            .role(RECRUTADOR.getRoleNome())
                            .imagemUrl("/img/profile/" + encodeToString(usuarioDTO.getEmail().getBytes()))
                            .build()
            );
        }
        return administradoresList;
    }
}