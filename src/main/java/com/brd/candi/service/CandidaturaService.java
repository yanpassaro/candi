package com.brd.candi.service;

import com.brd.candi.domain.dto.CandidaturaDTO;
import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.entity.*;
import com.brd.candi.domain.model.CandidaturaModel;
import com.brd.candi.domain.model.UsuarioModel;
import com.brd.candi.domain.model.VagaModel;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.CandidaturaRepository;
import com.brd.candi.repository.RespostaRepository;
import com.brd.candi.repository.UsuarioRepository;
import com.brd.candi.repository.VagaRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.brd.candi.domain.enumaration.Status.PENDENTE;
import static java.time.LocalDate.now;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableAsync
public class CandidaturaService {
    final CandidaturaRepository candidaturaRepository;
    final VagaRepository vagaRepository;
    final RespostaRepository respostaRepository;
    final UsuarioRepository usuarioRepository;
    final EmailSenderService emailSenderService;

    @Transactional
    public void cadastrar(CandidaturaDTO candidaturaDTO, UUID vaga, UUID id)
            throws AlreadyExistsException {
        if (!candidaturaRepository.existsCandidaturaByVagaIdAndCandidatoId(vaga, id))
            throw new AlreadyExistsException("Candidatura já realizada");
        log.info("Novo registro para a vaga {} do usuário {}", vaga, id);
        Usuario usuario = usuarioRepository.findUsuarioById(id);
        Candidatura candidatura = candidaturaRepository.save(
                Candidatura.builder()
                        .candidato(usuario)
                        .dataEnvio(now())
                        .status(PENDENTE.getStatusNome())
                        .vaga(vagaRepository.findVagaById(vaga))
                        .respostas(respostaRepository.saveAll(candidaturaDTO.getRespostas()))
                        .build()
        );
        emailSenderService.enviarEmail(EmailDTO.builder()
                .destino(candidatura.getCandidato().getEmail())
                .assunto("Candidatura realizada com sucesso")
                .conteudo("Sua candidatura para a vaga " + candidatura.getVaga().getNome() +
                        " foi enviada para análise, para mais informações entre em contato:" + candidatura.getVaga().getEmpresa().getContato().getEmail())
                .build());
    }

    @Async
    @Transactional
    public void deletar(UUID id, UUID candidato)
            throws NotAuthorizedException {
        if (!candidaturaRepository.existsCandidaturaByIdAndCandidatoId(id, candidato))
            throw new NotAuthorizedException("Candidatura não existe");
        log.info("Deletando a candidatura {}", id);
        Candidatura candidatura = candidaturaRepository.findCandidaturasById(id);
        respostaRepository.deleteAll(candidatura.getRespostas());
        candidaturaRepository.deleteById(id);
    }

    public List<CandidaturaModel> visualizar(UUID id, Integer page)
            throws NotExistException {
        if (!candidaturaRepository.existsCandidaturaByCandidatoId(id))
            throw new NotExistException("Nenhuma candidatura existente");
        Pageable pageable = PageRequest.of(page, 20);
        log.info("Retornando todas as candidaturas para o candidato {}", id);
        return candidaturaRepository.findCandidaturasByCandidatoId(id, pageable).map(
                candidatura -> CandidaturaModel.builder()
                        .id(candidatura.getId())
                        .status(candidatura.getStatus())
                        .dataEnvio(candidatura.getDataEnvio())
                        .vaga(VagaModel.builder()
                                .nome(candidatura.getVaga().getNome())
                                .dataTermino(candidatura.getVaga().getDataTermino())
                                .build())
                        .build()
        ).toList();
    }

    public List<CandidaturaModel> visualizar(UUID id, UUID admin, Integer page)
            throws NotAuthorizedException {
        if (candidaturaRepository.existsCandidaturaByVagaEmpresaRecrutadoresId(admin))
            throw new NotAuthorizedException("Não existe candidaturas para essa vaga ");
        Pageable pageable = PageRequest.of(page, 20);
        log.info("Retornando todas as candidaturas da vaga {}", id);
        return candidaturaRepository.findCandidaturasByVagaId(id, pageable).map(
                candidatura -> CandidaturaModel.builder()
                        .id(candidatura.getId())
                        .candidato(UsuarioModel.builder()
                                .nome(candidatura.getCandidato().getNome())
                                .build())
                        .status(candidatura.getStatus())
                        .dataEnvio(candidatura.getDataEnvio())
                        .vaga(VagaModel.builder()
                                .nome(candidatura.getVaga().getNome())
                                .build())
                        .build()
        ).toList();
    }

    public CandidaturaModel detalhar(UUID id, UUID admin) throws NotExistException {
        if (candidaturaRepository.existsCandidaturaByIdAndVagaEmpresaRecrutadoresId(id, admin))
            throw new NotExistException("Candidatura não existe");
        log.info("Retornando a candidatura {}", id);
        Candidatura candidatura = candidaturaRepository.findCandidaturasById(id);
        Vaga vaga = vagaRepository.findVagaById(candidatura.getVaga().getId());
        return CandidaturaModel.builder()
                .candidato(UsuarioModel.builder()
                        .nome(candidatura.getCandidato().getNome())
                        .sobrenome(candidatura.getCandidato().getSobrenome())
                        .contato(candidatura.getCandidato().getContato())
                        .endereco(candidatura.getCandidato().getEndereco())
                        .sobre(candidatura.getCandidato().getSobre())
                        .build())
                .status(candidatura.getStatus())
                .dataEnvio(candidatura.getDataEnvio())
                .vaga(VagaModel.builder()
                        .nome(candidatura.getVaga().getNome())
                        .build())
                .respostas(candidatura.getRespostas().stream().map(Resposta::getRespostaC).toList())
                .perguntas(vaga.getPerguntas().stream().map(Pergunta::getPerguntaV).toList())
                .build();
    }
}

