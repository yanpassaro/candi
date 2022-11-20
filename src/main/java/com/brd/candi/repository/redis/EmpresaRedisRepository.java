package com.brd.candi.repository.redis;

import com.brd.candi.model.redis.EmpresaRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmpresaRedisRepository extends CrudRepository<EmpresaRedis, UUID> {
}
