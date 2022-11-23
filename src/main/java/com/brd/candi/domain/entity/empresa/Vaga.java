package com.brd.candi.domain.entity.empresa;

import com.brd.candi.domain.entity.empresa.Empresa;
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
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String tipo;
    private String beneficios;
    private String sobre;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataTermino;
    @ManyToOne
    private Empresa empresa;
}
