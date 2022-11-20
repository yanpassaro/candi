package com.brd.candi.model.redis;

import com.brd.candi.model.entity.Endereco;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@Builder
@RedisHash
public class EmpresaRedis {
    @Id
    private UUID id;
    private String nome;
    private String cnpj;
    private String sobre;
    private String imagemUrl;
    private Endereco endereco;
}
