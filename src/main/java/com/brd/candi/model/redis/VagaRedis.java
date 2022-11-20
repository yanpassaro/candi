package com.brd.candi.model.redis;


import com.brd.candi.model.entity.Empresa;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@RedisHash
public class VagaRedis {
    @Id
    @Indexed
    private UUID id;
    private String nome;
    @Indexed
    private String tipo;
    private String experiencia;
    private String beneficios;
    private String sobre;
    private LocalDate dataTermino;
    private Empresa empresa;
}
