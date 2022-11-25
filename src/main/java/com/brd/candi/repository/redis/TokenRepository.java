package com.brd.candi.repository.redis;

import com.brd.candi.domain.redis.TokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<TokenRedis, UUID> {
    boolean existsTokenRedisByToken(UUID token);

    boolean existsTokenRedisByIdUser(UUID id);

    TokenRedis findTokenRedisByIdUser(UUID id);

    TokenRedis findTokenRedisByToken(UUID token);
}
