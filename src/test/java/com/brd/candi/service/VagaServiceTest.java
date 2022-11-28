package com.brd.candi.service;

import com.brd.candi.domain.entity.Vaga;
import com.brd.candi.repository.PerguntaRepository;
import com.brd.candi.repository.VagaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class VagaServiceTest {
    @MockBean
    private VagaRepository vagaRepository;
    @MockBean
    private PerguntaRepository perguntaRepository;


    @Test
    void cadastrar() {
    }

    @Test
    void deletar() {
        UUID id = UUID.randomUUID();
        Vaga vaga = vagaRepository.findVagaById(id);
        perguntaRepository.deleteById(id);
        vagaRepository.deleteById(id);
    }

    @Test
    void visualizarTodas() {
    }

    @Test
    void visualizar() {
    }
}