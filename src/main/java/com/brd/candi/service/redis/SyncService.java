package com.brd.candi.service.redis;

import com.brd.candi.model.entity.Usuario;
import com.brd.candi.model.redis.UsuarioRedis;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.UsuarioRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class SyncService {
    final UsuarioRedisRepository usuarioRedisRepository;
    final UsuarioRepository usuarioRepository;

    @Scheduled(fixedDelay = 1000 * 60)
    public void syncUsuario() {
        log.info("Sincronizando todos os usuários");
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarioRedisRepository.saveAll(loadUsuario(usuarios));
    }

    private List<UsuarioRedis> loadUsuario(List<Usuario> usuarios) {
        List<UsuarioRedis> usuarioRedis = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioRedis.add(
                    UsuarioRedis.builder()
                            .id(usuario.getId())
                            .email(usuario.getEmail())
                            .senha(usuario.getSenha())
                            .build()
            );
        }
        return usuarioRedis;
    }
}
