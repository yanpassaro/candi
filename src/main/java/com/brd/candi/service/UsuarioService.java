package com.brd.candi.service;

import com.brd.candi.model.dto.UsuarioDTO;
import com.brd.candi.model.entity.Usuario;
import com.brd.candi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.brd.candi.model.enumaration.Role.CANDIDATO;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(UsuarioDTO usuario) {
        log.info("Cadastrando novo usuário, email: {}", usuario.getEmail());
        return usuarioRepository.save(
                Usuario.builder()
                        .email(usuario.getEmail())
                        .senha(usuario.getSenha())
                        .nome(usuario.getNome())
                        .sobrenome(usuario.getSobrenome())
                        .token(UUID.randomUUID())
                        .tokenExpiredData(LocalDate.now().plusDays(7))
                        .role(CANDIDATO.getRoleNome())
                        .build()
        );
    }

    @Transactional
    public List<Usuario> salvarRecrutadores(List<UsuarioDTO> recrutadores) {
        log.info("Cadastrando novos recrutadores, {}", recrutadores);
        List<Usuario> usuarioList = new ArrayList<>();
        for (UsuarioDTO usuario : recrutadores) {
            usuarioList.add(
                    Usuario.builder()
                            .email(usuario.getEmail())
                            .senha(usuario.getSenha())
                            .nome(usuario.getNome())
                            .sobrenome(usuario.getSobrenome())
                            .token(UUID.randomUUID())
                            .tokenExpiredData(LocalDate.now().plusDays(7))
                            .role(CANDIDATO.getRoleNome())
                            .build()
            );
        }
        return usuarioRepository.saveAll(usuarioList);
    }
}
