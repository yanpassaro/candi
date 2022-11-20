package com.brd.candi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Formacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String instituto;
    private String curso;
    private String sobre;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataTermino;
}
