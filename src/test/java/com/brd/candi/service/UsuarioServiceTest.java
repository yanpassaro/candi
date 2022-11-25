package com.brd.candi.service;

import com.brd.candi.domain.dto.AtividadeDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioServiceTest {
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private UsuarioService usuarioService;
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
                        .dataInicio(LocalDate.now())
                        .build()))
                .build();
    }

    @Test
    void salvarUsuariosNoBanco() throws AlreadyExistsException {
        usuarioService.cadastrar(usuarioDTO);
    }

    @Test
    void retornarUsuariosDoBanco() {
        usuarioRepository.findAll();
    }
}