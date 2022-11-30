package com.brd.candi.service;

import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.dto.EmpresaDTO;
import com.brd.candi.domain.entity.empresa.Empresa;
import com.brd.candi.domain.model.EmpresaModel;
import com.brd.candi.exception.custom.IncorrectCedentialsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.repository.AdminRepository;
import com.brd.candi.repository.ContatoRepository;
import com.brd.candi.repository.EmpresaRepository;
import com.brd.candi.repository.EnderecoRepository;
import com.brd.candi.service.others.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.brd.candi.domain.enumaration.Role.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaService {
    final EmpresaRepository empresaRepository;
    final AdminRepository adminRepository;
    final EnderecoRepository enderecoRepository;
    final EmailSenderService emailSenderService;
    final ContatoRepository contatoRepository;

    @Transactional
    public void salvar(EmpresaDTO empresaDTO)
            throws IncorrectCedentialsException {
        if (empresaRepository.existsEmpresaByCnpj(empresaDTO.getCnpj()))
            throw new IncorrectCedentialsException("CNPJ já cadastrado");
        if (empresaDTO.getAdmins().stream().anyMatch(admin -> adminRepository.existsAdminByEmail(admin.getEmail())))
            throw new IncorrectCedentialsException("Email já cadastrado");
        Empresa empresa = empresaRepository.save(
                Empresa.builder()
                        .cnpj(empresaDTO.getCnpj())
                        .nome(empresaDTO.getNome())
                        .sobre(empresaDTO.getSobre())
                        .endereco(enderecoRepository.save(empresaDTO.getEndereco()))
                        .contato(contatoRepository.save(empresaDTO.getContato()))
                        .recrutadores(adminRepository.saveAll(new HashSet<>(empresaDTO.getAdmins())))
                        .build()
        );
        log.info("Cadastrando nova empresa {}", empresa.getId());
        if (!empresa.getContato().getEmail().isBlank()) {
            emailSenderService.enviarEmail(EmailDTO.builder()
                    .destino(empresaDTO.getContato().getEmail())
                    .assunto("Sua empresa foi cadastrada")
                    .conteudo("O cadastro da empresa " + empresaDTO.getNome() + " foi realizado com sucesso.")
                    .build()
            );
        }
    }

    @Transactional
    public void atualizar(EmpresaDTO empresaDTO, UUID id)
            throws NotAuthorizedException {
        if (!empresaRepository.existsByRecrutadoresId(id))
            throw new NotAuthorizedException("Sem autorização para realizar operação");
        Empresa empresa = empresaRepository.findEmpresaByRecrutadoresId(id);
        empresaRepository.save(
                Empresa.builder()
                        .id(empresa.getId())
                        .cnpj(empresaDTO.getCnpj())
                        .nome(empresaDTO.getNome())
                        .sobre(empresaDTO.getSobre())
                        .endereco(enderecoRepository.save(empresaDTO.getEndereco()))
                        .contato(contatoRepository.save(empresaDTO.getContato()))
                        .recrutadores(adminRepository.saveAll(empresaDTO.getAdmins()))
                        .build()
        );
        log.info("Atualizando empresa {}", empresa.getId());
    }

    @Async
    @Transactional
    public void deletar(UUID id) throws NotAuthorizedException {
        if (!empresaRepository.existsByRecrutadoresId(id))
            throw new NotAuthorizedException("Sem autorização para realizar operação");
        Empresa empresa = empresaRepository.findEmpresaByRecrutadoresId(id);
        log.info("Deletando a empresa {}", empresa.getId());
        empresa.getRecrutadores().forEach(admin -> {
            adminRepository.deleteById(admin.getId());
            log.info("Deletando o admin {}", admin.getId());
        });
        enderecoRepository.deleteById(empresa.getEndereco().getId());
        empresaRepository.deleteById(empresa.getId());
        if (!empresa.getContato().getEmail().isBlank()) {
            emailSenderService.enviarEmail(EmailDTO.builder()
                    .destino(empresa.getContato().getEmail())
                    .assunto("Conta excluída")
                    .conteudo("Empresa foi excluída com sucesso.")
                    .build()
            );
        }
    }

    public List<EmpresaModel> visualizar(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        log.info("Listando empresas");
        return empresaRepository.findAll(pageable).map(
                empresa -> EmpresaModel.builder()
                        .nome(empresa.getNome())
                        .cnpj(empresa.getCnpj())
                        .sobre(empresa.getSobre())
                        .email(empresa.getContato().getEmail())
                        .site(empresa.getContato().getSite())
                        .cidade(empresa.getEndereco().getCidade())
                        .estado(empresa.getEndereco().getEstado())
                        .telefone(empresa.getContato().getTelefone())
                        .build()
        ).toList();
    }

    public Empresa detalhar(UUID id) throws NotExistException {
        if (!empresaRepository.existsByRecrutadoresId(id)) {
            throw new NotExistException("Administrador não cadastrado");
        }
        return empresaRepository.findEmpresaByRecrutadoresId(id);
    }
}
