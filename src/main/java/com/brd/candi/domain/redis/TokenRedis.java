package com.brd.candi.domain.redis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
@RedisHash
public class TokenRedis {
    @Id
    @Indexed
    private UUID token;
    @Indexed
    @JsonIgnore
    private UUID idUser;
    private String role;
}
