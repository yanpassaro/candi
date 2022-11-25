package com.brd.candi.repository;

import com.brd.candi.domain.entity.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Empresa findEmpresaByRecrutadoresId(UUID id);

    boolean existsEmpresaByCnpj(String cnpj);
}
