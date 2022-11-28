package com.brd.candi.service;

import com.brd.candi.domain.dto.VagaDTO;
import com.brd.candi.domain.entity.Pergunta;
import com.brd.candi.domain.entity.Vaga;
import com.brd.candi.domain.entity.empresa.Empresa;
import com.brd.candi.domain.model.EmpresaModel;
import com.brd.candi.domain.model.VagaModel;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.CandidaturaRepository;
import com.brd.candi.repository.EmpresaRepository;
import com.brd.candi.repository.PerguntaRepository;
import com.brd.candi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VagaService {
    final VagaRepository vagaRepository;
    final EmpresaRepository empresaRepository;
    final PerguntaRepository perguntaRepository;
    final CandidaturaRepository candidaturaRepository;

    @Transactional
    public void cadastrar(VagaDTO vagaDTO, UUID id) throws AlreadyExistsException {
        if (vagaRepository.existsVagaByNomeAndEmpresaRecrutadoresId(vagaDTO.getNome(), id))
            throw new AlreadyExistsException("Vaga já foi cadastrada por essa empresa");
        Empresa empresa = empresaRepository.findEmpresaByRecrutadoresId(id);
        log.info("Cadastrando uma nova vaga na empresa '{}'", empresa.getId());
        vagaRepository.save(
                Vaga.builder()
                        .empresa(empresa)
                        .beneficios(vagaDTO.getBeneficios())
                        .nome(vagaDTO.getNome())
                        .sobre(vagaDTO.getSobre())
                        .tipo(vagaDTO.getTipo().getTipoNome())
                        .experiencia(vagaDTO.getExperiencia())
                        .perguntas(perguntaRepository.saveAll(vagaDTO.getPerguntas()))
                        .build()
        );
    }

    @Transactional
    public void deletar(UUID id, UUID admin) throws NotAuthorizedException {
        if (!vagaRepository.existsVagaByEmpresaRecrutadoresId(admin)) {
            throw new NotAuthorizedException("Vaga não cadastrada");
        }
        log.info("Deletando vaga {}", id);
        Vaga vaga = vagaRepository.findVagaById(id);
        perguntaRepository.deleteAll(vaga.getPerguntas());
        candidaturaRepository.deleteAllByVagaId(id);
        vagaRepository.deleteById(id);
    }

    public List<VagaModel> visualizar(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        log.info("Retornando todas as vagas");
        return vagaRepository.findAll(pageable).map(
                vaga -> VagaModel.builder()
                        .id(vaga.getId())
                        .nome(vaga.getNome())
                        .tipo(vaga.getTipo())
                        .dataTermino(vaga.getDataTermino())
                        .empresa(EmpresaModel.builder()
                                .nome(vaga.getEmpresa().getNome())
                                .cidade(vaga.getEmpresa().getEndereco().getCidade())
                                .build())
                        .build()
        ).toList();
    }

    public VagaModel detalhar(UUID id) throws NotExistException {
        if (!vagaRepository.existsById(id)) {
            log.warn("Vaga não existe {}", id);
            throw new NotExistException("Vaga não cadastrada");
        }
        log.info("Retornando vaga {}", id);
        Vaga vaga = vagaRepository.findVagaById(id);
        return VagaModel.builder()
                .nome(vaga.getNome())
                .tipo(vaga.getTipo())
                .experiencia(vaga.getExperiencia())
                .sobre(vaga.getSobre())
                .beneficios(vaga.getBeneficios())
                .dataTermino(vaga.getDataTermino())
                .perguntas(vaga.getPerguntas().stream().map(Pergunta::getPerguntaV).toList())
                .empresa(EmpresaModel.builder()
                        .nome(vaga.getEmpresa().getNome())
                        .cnpj(vaga.getEmpresa().getCnpj())
                        .email(vaga.getEmpresa().getContato().getEmail())
                        .telefone(vaga.getEmpresa().getContato().getTelefone())
                        .site(vaga.getEmpresa().getContato().getBlog())
                        .cidade(vaga.getEmpresa().getEndereco().getCidade())
                        .estado(vaga.getEmpresa().getEndereco().getEstado())
                        .build())
                .build();
    }
}
