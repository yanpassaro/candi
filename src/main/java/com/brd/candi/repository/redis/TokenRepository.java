package com.brd.candi.repository.redis;

import com.brd.candi.domain.redis.TokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<TokenRedis, UUID> {
    boolean existsByIdAndToken(UUID id, UUID token);

    TokenRedis findTokenRedisById(UUID id);
}
