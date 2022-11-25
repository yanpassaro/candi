package com.brd.candi.service;

import com.brd.candi.domain.dto.EmailDTO;
import com.brd.candi.domain.dto.EmpresaDTO;
import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
import com.brd.candi.domain.entity.empresa.Admin;
import com.brd.candi.domain.entity.empresa.Empresa;
import com.brd.candi.domain.model.EmpresaModel;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
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
import java.util.stream.Collectors;

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
    public void salvar(EmpresaDTO empresaDTO) throws AlreadyExistsException {
        if (empresaRepository.existsEmpresaByCnpj(empresaDTO.getCnpj()))
            throw new AlreadyExistsException("CNPJ já foi cadastrado");
        Empresa empresa = empresaRepository.save(
                Empresa.builder()
                        .cnpj(empresaDTO.getCnpj())
                        .nome(empresaDTO.getNome())
                        .sobre(empresaDTO.getSobre())
                        .imagemUrl(empresaDTO.getImagemUrl())
                        .endereco(enderecoRepository.save(
                                Endereco.builder()
                                        .cep(empresaDTO.getEndereco().getCep())
                                        .cidade(empresaDTO.getEndereco().getCidade())
                                        .estado(empresaDTO.getEndereco().getEstado())
                                        .build()
                        ))
                        .contato(contatoRepository.save(
                                Contato.builder()
                                        .email(empresaDTO.getContato().getEmail())
                                        .blog(empresaDTO.getContato().getBlog())
                                        .telefone(empresaDTO.getContato().getTelefone())
                                        .portfolio(empresaDTO.getContato().getPortfolio())
                                        .build()
                        ))
                        .recrutadores(new HashSet<>(adminRepository.saveAll(
                                empresaDTO.getAdministradores().stream().map(
                                        adminDTO -> {
                                            return Admin.builder()
                                                    .nome(adminDTO.getNome())
                                                    .sobrenome(adminDTO.getSobrenome())
                                                    .email(adminDTO.getEmail())
                                                    .senha(adminDTO.getSenha())
                                                    .role(adminDTO.getRole().getRoleNome())
                                                    .sobre(adminDTO.getSobre())
                                                    .build();
                                        }

                                ).limit(5).collect(Collectors.toSet()))))
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
        if (adminRepository.existsAdminByIdAndRole(id, ADMIN.getRoleNome()))
            throw new NotAuthorizedException("Sem autorização para realizar operação");
        Empresa empresa = empresaRepository.findEmpresaByRecrutadoresId(id);
        empresaRepository.save(
                Empresa.builder()
                        .id(empresa.getId())
                        .cnpj(empresaDTO.getCnpj())
                        .nome(empresaDTO.getNome())
                        .sobre(empresaDTO.getSobre())
                        .imagemUrl(empresaDTO.getImagemUrl())
                        .endereco(enderecoRepository.save(
                                Endereco.builder()
                                        .id(empresa.getEndereco().getId())
                                        .cep(empresaDTO.getEndereco().getCep())
                                        .cidade(empresaDTO.getEndereco().getCidade())
                                        .estado(empresaDTO.getEndereco().getEstado())
                                        .build()
                        ))
                        .contato(contatoRepository.save(
                                Contato.builder()
                                        .id(empresa.getContato().getId())
                                        .email(empresaDTO.getContato().getEmail())
                                        .blog(empresaDTO.getContato().getBlog())
                                        .telefone(empresaDTO.getContato().getTelefone())
                                        .portfolio(empresaDTO.getContato().getPortfolio())
                                        .build()
                        ))
                        .recrutadores(new HashSet<>(adminRepository.saveAll(
                                empresaDTO.getAdministradores().stream().map(
                                        adminDTO -> {
                                            return Admin.builder()
                                                    .id(adminDTO.getId())
                                                    .nome(adminDTO.getNome())
                                                    .sobrenome(adminDTO.getSobrenome())
                                                    .email(adminDTO.getEmail())
                                                    .senha(adminDTO.getSenha())
                                                    .role(adminDTO.getRole().getRoleNome())
                                                    .sobre(adminDTO.getSobre())
                                                    .build();
                                        }
                                ).limit(5).collect(Collectors.toSet()))))
                        .build()
        );
        log.info("Atualizando empresa {}", empresa.getId());
    }

    @Async
    @Transactional
    public void deletar(UUID id) throws NotAuthorizedException {
        if (adminRepository.existsAdminByIdAndRole(id, ADMIN.getRoleNome()))
            throw new NotAuthorizedException("Sem autorização para realizar operação");
        Empresa empresa = empresaRepository.findEmpresaByRecrutadoresId(id);
        log.info("Deletando empresa {}", empresa.getId());
        empresa.getRecrutadores().forEach(admin -> {
            adminRepository.deleteById(admin.getId());
            log.info("Deletando admin {}", admin.getId());
        });
        enderecoRepository.deleteById(empresa.getEndereco().getId());
        empresaRepository.deleteById(empresa.getId());
        if (!empresa.getEmail().isBlank()) {
            emailSenderService.enviarEmail(EmailDTO.builder()
                    .destino(empresa.getEmail())
                    .assunto("Sua empresa foi excluída")
                    .conteudo("Sua empresa foi deletada com sucesso.")
                    .build()
            );
        }
    }

    public List<EmpresaModel> visualizar(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        log.info("Listando empresas");
        return empresaRepository.findAll(pageable).map(
                empresa -> {
                    return EmpresaModel.builder()
                            .nome(empresa.getNome())
                            .cnpj(empresa.getCnpj())
                            .sobre(empresa.getSobre())
                            .email(empresa.getContato().getEmail())
                            .telefone(empresa.getContato().getTelefone())
                            .build();
                }
        ).toList();
    }
}
