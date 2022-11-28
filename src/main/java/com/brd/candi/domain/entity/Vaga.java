package com.brd.candi.domain.entity;

import com.brd.candi.domain.entity.empresa.Empresa;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String tipo;
    private String beneficios;
    private String sobre;
    private String experiencia;
    private LocalDate dataTermino;
    @ManyToOne
    private Empresa empresa;
    @OneToMany
    @ToString.Exclude
    private List<Pergunta> perguntas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vaga vaga = (Vaga) o;
        return id != null && Objects.equals(id, vaga.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
