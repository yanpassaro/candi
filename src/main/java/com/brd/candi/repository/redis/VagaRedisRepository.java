package com.brd.candi.repository.redis;

import com.brd.candi.model.redis.VagaRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VagaRedisRepository extends CrudRepository<VagaRedis, UUID> {
}
