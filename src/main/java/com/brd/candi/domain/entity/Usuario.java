package com.brd.candi.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
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
    @Column(unique = true, nullable = false)
    private String email;
    private String senha;
    private String sobre;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Contato contato;
    @OneToMany
    @ToString.Exclude
    private Set<Atividade> atividades;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Usuario usuario = (Usuario) o;
        return email != null && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
