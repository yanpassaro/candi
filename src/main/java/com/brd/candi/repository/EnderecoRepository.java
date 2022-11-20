package com.brd.candi.repository;

import com.brd.candi.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@EnableJpaRepositories
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
