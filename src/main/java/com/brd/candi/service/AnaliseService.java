package com.brd.candi.service;

import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.entity.Candidatura;
import com.brd.candi.domain.enumaration.Status;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.repository.CandidaturaRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDate.now;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnaliseService {
    final CandidaturaRepository candidaturaRepository;
    final EmailSenderService emailSenderService;

    public void analisar(Status status, UUID id, UUID admin)
            throws NotAuthorizedException {
        if (!candidaturaRepository.existsCandidaturaByIdAndVagaEmpresaRecrutadoresId(id, admin))
            throw new NotAuthorizedException("Nenhuma candidatura encontrada");
        log.info("Nova analise para a candidatura {}", id);
        Candidatura candidatura = candidaturaRepository.findCandidaturasById(id);
        candidaturaRepository.save(
                Candidatura.builder()
                        .candidato(candidatura.getCandidato())
                        .dataEnvio(now())
                        .status(status.getStatusNome())
                        .vaga(candidatura.getVaga())
                        .respostas(candidatura.getRespostas())
                        .build()
        );
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(candidatura.getCandidato().getEmail())
                .assunto("Nova atualização de candidatura")
                .conteudo("Você foi " + status.getStatusNome() +
                        " para a vaga " + candidatura.getVaga().getNome() +
                        "\nPara mais informações entre em contato com:" +
                        candidatura.getVaga().getEmpresa().getContato().getEmail())
                .build()
        );
    }
}
