package com.brd.candi.repository.redis;

import com.brd.candi.model.redis.UsuarioRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsuarioRedisRepository extends CrudRepository<UsuarioRedis, UUID> {
}
