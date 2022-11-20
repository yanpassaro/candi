package com.brd.candi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String role;
    private String imagemUrl;
    @Column(unique = true)
    private UUID token;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate tokenExpiredData;
}
