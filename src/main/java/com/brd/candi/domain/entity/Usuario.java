package com.brd.candi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
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
    private String nome;
    private String sobrenome;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String senha;
    @JsonIgnore
    private String role;
    private String imagemUrl;
    @OneToOne
    private Endereco endereco;
    @OneToMany
    private Set<Atividade> atividades;
}
