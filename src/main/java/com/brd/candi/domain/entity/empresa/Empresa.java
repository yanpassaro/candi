package com.brd.candi.domain.entity.empresa;

import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String cnpj;
    private String sobre;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Contato contato;
    @OneToMany
    @ToString.Exclude
    private List<Admin> recrutadores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Empresa empresa = (Empresa) o;
        return id != null && Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
