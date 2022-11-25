package com.brd.candi.service.redis;

import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.domain.entity.empresa.Admin;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.AdminRepository;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.brd.candi.domain.enumaration.Role.USUARIO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final TokenRepository tokenRepository;
    final UsuarioRepository usuarioRepository;
    final AdminRepository adminRepository;

    public TokenRedis autenticar(UUID token) throws NotAuthorizedException {
        if (!tokenRepository.existsTokenRedisByToken(token)) {
            log.error("Credenciais {} inválidas", token);
            throw new NotAuthorizedException("Token invalidas");
        }
        return tokenRepository.findTokenRedisByToken(token);
    }

    @Transactional
    public TokenRedis login(LoginDTO loginDTO) throws NotExistException {
        if (!usuarioRepository.existsByEmail(loginDTO.getEmail()))
            throw new NotExistException("Usuário não cadastrado");
        if (!usuarioRepository.existsByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha()))
            throw new NotExistException("Senha não coincide");
        Usuario usuario = usuarioRepository.findUsuarioByEmail(loginDTO.getEmail());
        if (!tokenRepository.existsTokenRedisByIdUser(usuario.getId())) {
            log.info("Retornando nova autorização para o usuário {}", usuario.getId());
            return tokenRepository.save(TokenRedis.builder()
                    .idUser(usuario.getId())
                    .token(UUID.randomUUID())
                    .role(USUARIO.getRoleNome())
                    .build()
            );
        }
        log.info("Retornando autorização para o admin {}", usuario.getId());
        return tokenRepository.findTokenRedisByIdUser(usuario.getId());
    }

    @Transactional
    public TokenRedis loginAdmin(LoginDTO loginDTO) throws NotExistException {
        if (!adminRepository.existsAdminByEmail(loginDTO.getEmail()))
            throw new NotExistException("Admin não cadastrado");
        if (!adminRepository.existsAdminByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha()))
            throw new NotExistException("Senha não coincide");
        Admin admin = adminRepository.findAdminByEmail(loginDTO.getEmail());
        if (!tokenRepository.existsTokenRedisByIdUser(admin.getId())) {
            log.info("Retornando nova autorização para o admin {}", admin.getId());
            return tokenRepository.save(TokenRedis.builder()
                    .idUser(admin.getId())
                    .token(UUID.randomUUID())
                    .role(admin.getRole())
                    .build()
            );
        }
        log.info("Retornando autorização para o admin {}", admin.getId());
        return tokenRepository.findTokenRedisByIdUser(admin.getId());
    }
}
