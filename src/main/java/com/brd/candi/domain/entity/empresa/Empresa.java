package com.brd.candi.domain.entity.empresa;

import com.brd.candi.domain.entity.Endereco;
import com.brd.candi.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriado;
    private String nome;
    @Column(unique = true)
    private String cnpj;
    private String sobre;
    private String imagemUrl;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Usuario administrador;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> administradores;

}
