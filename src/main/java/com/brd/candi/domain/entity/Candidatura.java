package com.brd.candi.domain.entity;

import com.brd.candi.domain.entity.empresa.Vaga;
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
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEnvio;
    @ManyToOne
    private Vaga vaga;
    @ManyToOne
    private Usuario candidato;
}
