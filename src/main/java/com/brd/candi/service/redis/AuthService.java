package com.brd.candi.service.redis;

import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.repository.redis.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final TokenRepository tokenRepository;

    public void verificar(UUID id, UUID token) throws NotAuthorizedException {
        if (!tokenRepository.existsByIdAndToken(id, token)) {
            log.error("Credenciais {} {} inválidas", id, token);
            throw new NotAuthorizedException("Credenciais invalidas");
        }
    }
}
