package com.brd.candi.service;

import com.brd.candi.repository.PerguntaRepository;
import com.brd.candi.repository.VagaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class VagaServiceTest {
    @MockBean
    private VagaRepository vagaRepository;
    @MockBean
    private PerguntaRepository perguntaRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void cadastrar() {
    }

    @Test
    void deletar() {
    }

    @Test
    void visualizar() {
    }

    @Test
    void visualizarEmpresa() {
    }

    @Test
    void detalhar() {
    }
}