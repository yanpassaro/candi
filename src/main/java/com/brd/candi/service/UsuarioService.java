package com.brd.candi.service;

import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.AtividadeRepository;
import com.brd.candi.repository.EnderecoRepository;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.TokenRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

import static com.brd.candi.domain.enumaration.Role.CANDIDATO;
import static org.springframework.util.Base64Utils.encodeToString;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    final UsuarioRepository usuarioRepository;
    final EnderecoRepository enderecoRepository;
    final AtividadeRepository atividadeRepository;
    final TokenRepository tokenRepository;
    final EmailSenderService emailSenderService;

    @Transactional
    public void salvar(UsuarioDTO usuarioDTO) throws AlreadyExistsException {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            log.warn("Tentativa de cadastro falhou");
            throw new AlreadyExistsException("Esse usuário já foi cadastrado");
        }
        log.info("Cadastrando novo usuário, email: {}", usuarioDTO.getEmail());
        Usuario usuario = usuarioRepository.save(
                Usuario.builder()
                        .email(usuarioDTO.getEmail())
                        .senha(usuarioDTO.getSenha())
                        .nome(usuarioDTO.getNome())
                        .sobrenome(usuarioDTO.getSobrenome())
                        .role(CANDIDATO.getRoleNome())
                        .endereco(enderecoRepository.save(usuarioDTO.getEndereco()))
                        .atividades(new HashSet<>(atividadeRepository.saveAll(usuarioDTO.getAtividades())))
                        .imagemUrl("/img/profile/" + encodeToString(usuarioDTO.getEmail().getBytes()))
                        .build()
        );
        tokenRepository.save(TokenRedis.builder()
                .id(usuario.getId())
                .token(UUID.randomUUID())
                .ativo(true)
                .build()
        );
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(usuario.getEmail())
                .assunto("Bem-vindo " + usuario.getNome() + usuario.getSobrenome())
                .conteudo("Seu novo cadastro no email: " + usuario.getEmail() + " foi realizado com sucesso!\n" +
                        "Se não foi você, desconsidere esse email :)")
                .build()
        );
    }

    public Usuario visualizar(UUID id) throws NotExistException {
        if (!usuarioRepository.existsById(id))
            throw new NotExistException("Esse ID não faz referencia a um usuário cadastrado");
        return usuarioRepository.findUsuarioById(id);
    }

    @Transactional
    public TokenRedis login(LoginDTO loginDTO) throws NotExistException {
        if (!usuarioRepository.existsByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha()))
            throw new NotExistException("Login ou senha não coincidem");
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
