package com.brd.candi.repository.redis;

import com.brd.candi.domain.redis.EmpresaRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmpresaRedisRepository extends CrudRepository<EmpresaRedis, UUID> {
}
