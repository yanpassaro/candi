package com.brd.candi.repository;

import com.brd.candi.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
}
