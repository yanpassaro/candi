package com.brd.candi.service;

import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.entity.Atividade;
import com.brd.candi.domain.entity.Endereco;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.repository.AtividadeRepository;
import com.brd.candi.repository.EnderecoRepository;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.redis.TokenRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.brd.candi.domain.enumaration.Role.CANDIDATO;
import static org.springframework.util.Base64Utils.encodeToString;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {
    final UsuarioRepository usuarioRepository;
    final EnderecoRepository enderecoRepository;
    final AtividadeRepository atividadeRepository;
    final TokenRepository tokenRepository;
    final EmailSenderService emailSenderService;

    @Transactional
    public void cadastrar(UsuarioDTO usuarioDTO) throws AlreadyExistsException {
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
                        .endereco(enderecoRepository.save(Endereco.builder()
                                .id(usuarioDTO.getEndereco().getId())
                                .cep(usuarioDTO.getEndereco().getCep())
                                .estado(usuarioDTO.getEndereco().getEstado())
                                .cidade(usuarioDTO.getEndereco().getCidade())
                                .build()))
                        .atividades(new HashSet<>(atividadeRepository.saveAll(usuarioDTO.getAtividades()
                                .stream().map(atividadeDTO -> {
                                    return Atividade.builder()
                                            .id(atividadeDTO.getId())
                                            .local(atividadeDTO.getLocal())
                                            .nome(atividadeDTO.getNome())
                                            .dataInicio(atividadeDTO.getDataInicio())
                                            .dataTermino(atividadeDTO.getDataTermino())
                                            .sobre(atividadeDTO.getSobre())
                                            .build();
                                }).collect(Collectors.toSet()))))
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
                .assunto("Bem-vindo " + usuario.getNome() + " " + usuario.getSobrenome())
                .conteudo("O cadastro no email: " + usuario.getEmail() + " foi realizado com sucesso!\n")
                .build()
        );
    }

    @Async
    @Transactional
    public void atualizar(UsuarioDTO usuarioDTO, UUID id) {
        log.info("Atualizando usuário {}", usuarioDTO);
        Usuario usuario = usuarioRepository.findUsuarioById(id);
        usuarioRepository.save(
                Usuario.builder()
                        .id(id)
                        .email(usuario.getEmail())
                        .imagemUrl(usuario.getImagemUrl())
                        .senha(usuarioDTO.getSenha())
                        .role(usuario.getRole())
                        .nome(usuarioDTO.getNome())
                        .endereco(enderecoRepository.save(Endereco.builder()
                                .id(usuarioDTO.getEndereco().getId())
                                .cep(usuarioDTO.getEndereco().getCep())
                                .estado(usuarioDTO.getEndereco().getEstado())
                                .cidade(usuarioDTO.getEndereco().getCidade())
                                .build()))
                        .atividades(new HashSet<>(atividadeRepository.saveAll(usuarioDTO.getAtividades()
                                .stream().map(atividadeDTO -> {
                                    return Atividade.builder()
                                            .id(atividadeDTO.getId())
                                            .local(atividadeDTO.getLocal())
                                            .nome(atividadeDTO.getNome())
                                            .dataInicio(atividadeDTO.getDataInicio())
                                            .dataTermino(atividadeDTO.getDataTermino())
                                            .sobre(atividadeDTO.getSobre())
                                            .build();
                                }).collect(Collectors.toSet()))))
                        .build()
        );
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(usuario.getEmail())
                .assunto("Cadastro atualizado")
                .conteudo("A atualização no email: " + usuario.getEmail() + " foi realizado com sucesso!\n")
                .build()
        );
    }

    @Async
    @Transactional
    public void deletar(UUID id) {
        log.info("Deletando usuário {}", id);
        Usuario usuario = usuarioRepository.findUsuarioById(id);
        usuarioRepository.deleteById(id);
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(usuario.getEmail())
                .assunto("Sua conta foi excluida")
                .conteudo("Sua conta email: " + usuario.getEmail() + " foi deletada com sucesso! (infelizmente... :c)\n")
                .build()
        );
    }

    public Usuario visualizar(UUID id) {
        log.info("Retornando usuário {}", id);
        return usuarioRepository.findUsuarioById(id);
    }
}
