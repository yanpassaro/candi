package com.brd.candi.repository.redis;

import com.brd.candi.model.redis.EmpresaRedis;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@EnableRedisRepositories
public interface EmpresaRedisRepository extends CrudRepository<EmpresaRedis, UUID> {
}
