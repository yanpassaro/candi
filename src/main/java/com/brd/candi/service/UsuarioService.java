package com.brd.candi.service;

import com.brd.candi.model.dto.UsuarioDTO;
import com.brd.candi.model.entity.Usuario;
import com.brd.candi.model.redis.UsuarioRedis;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.UsuarioRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    final UsuarioRedisRepository usuarioRedisRepository;
    final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioRedis salvar(UsuarioDTO usuarioDTO){
        log.info("Cadastrando novo usuário, email: {}", usuarioDTO.getEmail());
        Usuario usuario = usuarioRepository.save(Usuario.builder()
                        .email(usuarioDTO.getEmail())
                        .senha(usuarioDTO.getSenha())
                .build());
        return usuarioRedisRepository.save(UsuarioRedis.builder()
                        .id(usuario.getId())
                        .email(usuario.getEmail())
                        .senha(usuario.getSenha())
                .build());
    }

    public Iterable<UsuarioRedis> visualizarTodos(){
        return usuarioRedisRepository.findAll();
    }

}
