package com.brd.candi.service.redis;

import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final TokenRepository tokenRepository;
    final UsuarioRepository usuarioRepository;

    public void verificar(UUID id, UUID token) throws NotAuthorizedException {
        if (!tokenRepository.existsTokenRedisByIdAndToken(id, token)) {
            log.error("Credenciais {} {} inválidas", id, token);
            throw new NotAuthorizedException("Credenciais invalidas");
        }
    }

    @Transactional
    public TokenRedis login(LoginDTO loginDTO) throws NotExistException {
        if (!usuarioRepository.existsByEmail(loginDTO.getEmail()))
            throw new NotExistException("Usuário não cadastrado");
        if (!usuarioRepository.existsByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha()))
            throw new NotExistException("Senha não coincidem");
        Usuario usuario = usuarioRepository.findUsuarioByEmail(loginDTO.getEmail());
        if (!tokenRepository.existsById(usuario.getId()))
            tokenRepository.save(TokenRedis.builder()
                    .id(usuario.getId())
                    .token(UUID.randomUUID())
                    .ativo(true)
                    .build());
        return tokenRepository.findTokenRedisById(usuario.getId());
    }
}
