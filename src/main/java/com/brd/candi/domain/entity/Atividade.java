package com.brd.candi.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String local;
    private String sobre;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private boolean ativo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Atividade atividade = (Atividade) o;
        return id != null && Objects.equals(nome, atividade.nome);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
