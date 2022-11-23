package com.brd.candi.service;

import com.brd.candi.domain.dto.AtividadeDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.entity.Atividade;
import com.brd.candi.domain.entity.Usuario;
import com.brd.candi.repository.AtividadeRepository;
import com.brd.candi.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.brd.candi.domain.enumaration.Role.CANDIDATO;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private AtividadeRepository atividadeRepository;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void getUsuario() {
        usuarioDTO = UsuarioDTO.builder()
                .email("yan@gmail.com")
                .senha("senha")
                .nome("Yan")
                .atividades(Set.of(AtividadeDTO.builder()
                        .nome("nome")
                        .local("local")
                        .dataTermino(LocalDate.now())
                        .dataInicio(LocalDate.now())
                        .build()))
                .build();
    }

    @Test
    void salvarUsuariosNoBanco() {
        Usuario usuario = Usuario.builder()
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .nome(usuarioDTO.getNome())
                .sobrenome(usuarioDTO.getSobrenome())
                .role(CANDIDATO.getRoleNome())
                .atividades(new HashSet<>(atividadeRepository.saveAll(
                        usuarioDTO.getAtividades()
                                .stream().map(atividadeDTO -> {
                                    return Atividade.builder()
                                            .local(atividadeDTO.getLocal())
                                            .nome(atividadeDTO.getNome())
                                            .dataInicio(atividadeDTO.getDataInicio())
                                            .dataTermino(atividadeDTO.getDataTermino())
                                            .build();
                                }).collect(Collectors.toSet()))))
                .build();
        Assertions.assertNull(usuarioRepository.save(usuario), "Salvando usuário");
    }

    @Test
    void retornarUsuariosDoBanco() {
        Assertions.assertNull(usuarioRepository.findAll(), "Retornando usuários");
    }
}