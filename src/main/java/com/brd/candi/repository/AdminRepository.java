package com.brd.candi.repository;

import com.brd.candi.domain.entity.empresa.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findAdminByEmail(String email);

    boolean existsAdminByEmailAndSenha(String email, String senha);

    boolean existsAdminByIdAndRole(UUID id, String role);

    boolean existsAdminByEmail(String email);
}
