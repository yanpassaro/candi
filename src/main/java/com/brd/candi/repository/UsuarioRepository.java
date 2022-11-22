package com.brd.candi.repository;

import com.brd.candi.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndSenha(String email, String senha);

    Usuario findUsuarioByEmail(String email);

    Usuario findUsuarioById(UUID id);
}
