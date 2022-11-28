package com.brd.candi.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String respostaC;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resposta resposta = (Resposta) o;
        return id != null && Objects.equals(respostaC, resposta.respostaC);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}