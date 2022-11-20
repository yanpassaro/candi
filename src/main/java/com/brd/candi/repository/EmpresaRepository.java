package com.brd.candi.repository;

import com.brd.candi.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@EnableJpaRepositories
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
}
