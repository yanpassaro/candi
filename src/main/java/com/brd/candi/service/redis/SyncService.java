package com.brd.candi.service.redis;

import com.brd.candi.repository.redis.EmpresaRedisRepository;
import com.brd.candi.repository.redis.VagaRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@EnableRedisRepositories
@EnableScheduling
@Service
@RequiredArgsConstructor
public class SyncService {
    final EmpresaRedisRepository empresaRedisRepository;
    final VagaRedisRepository vagaRedisRepository;

    @Transactional
    @Scheduled(fixedDelay = 10000*60)
    public void flushAllEmpresas(){
        log.info("deletando todas as empresas");
        empresaRedisRepository.deleteAll();
    }

    @Transactional
    @Scheduled(fixedDelay = 10000*60)
    public void flushAllVagas(){
        log.info("deletando todas as vagas");
        vagaRedisRepository.deleteAll();
    }
}
