package com.brd.candi.repository.redis;

import com.brd.candi.model.redis.VagaRedis;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@EnableRedisRepositories
public interface VagaRedisRepository extends CrudRepository<VagaRedis, UUID> {
}
