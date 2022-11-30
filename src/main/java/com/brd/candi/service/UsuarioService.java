package com.brd.candi.service;

import com.brd.candi.domain.dto.AtualizarUsuarioDTO;
import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.ContatoRepository;
import com.brd.candi.repository.EnderecoRepository;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {
    final UsuarioRepository usuarioRepository;
    final EnderecoRepository enderecoRepository;
    final EmailSenderService emailSenderService;
    final ContatoRepository contatoRepository;

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
                        .endereco(enderecoRepository.save(usuarioDTO.getEndereco()))
                        .contato(contatoRepository.save(usuarioDTO.getContato()))
                        .build()
        );
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(usuario.getEmail())
                .assunto("Bem-vindo " + usuario.getNome() + " " + usuario.getSobrenome())
                .conteudo("O cadastro foi realizado com sucesso.")
                .build()
        );
    }

    @Async
    @Transactional
    public void atualizar(AtualizarUsuarioDTO usuarioDTO, UUID id) throws NotExistException {
        if (!usuarioRepository.existsById(id))
            throw new NotExistException("Usuário não cadastrado");
        log.info("Atualizando usuário {}", usuarioDTO);
        Usuario usuario = usuarioRepository.findUsuarioById(id);
        usuarioRepository.save(
                Usuario.builder()
                        .id(id)
                        .email(usuario.getEmail())
                        .sobrenome(usuarioDTO.getSobrenome())
                        .senha(usuarioDTO.getSenha())
                        .nome(usuario.getNome())
                        .endereco(enderecoRepository.save(usuarioDTO.getEndereco()))
                        .contato(contatoRepository.save(usuarioDTO.getContato()))
                        .build());
    }

    @Async
    @Transactional
    public void deletar(UUID id) throws NotExistException {
        if (!usuarioRepository.existsById(id))
            throw new NotExistException("Usuário não cadastrado");
        log.info("Deletando usuário {}", id);
        Usuario usuario = usuarioRepository.findUsuarioById(id);
        usuarioRepository.deleteById(id);
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(usuario.getEmail())
                .assunto("Sua conta foi excluída")
                .conteudo("Sua conta foi deletada com sucesso.")
                .build()
        );
    }

    public Usuario visualizar(UUID id) throws NotExistException {
        if (!usuarioRepository.existsById(id))
            throw new NotExistException("Usuário não cadastrado");
        log.info("Retornando usuário {}", id);
        return usuarioRepository.findUsuarioById(id);
    }
}
