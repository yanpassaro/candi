package com.brd.candi.model.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
@RedisHash("usuario")
public class UsuarioRedis {
    @Id
    @Indexed
    private UUID id;
    @Indexed
    private String email;
    private String senha;
}
