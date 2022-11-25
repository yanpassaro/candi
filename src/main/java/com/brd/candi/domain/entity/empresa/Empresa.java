package com.brd.candi.domain.entity.empresa;

import com.brd.candi.domain.entity.Contato;
import com.brd.candi.domain.entity.Endereco;
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
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String cnpj;
    private String email;
    private String sobre;
    private String imagemUrl;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Contato contato;
    @OneToMany
    private Set<Admin> recrutadores;

}
