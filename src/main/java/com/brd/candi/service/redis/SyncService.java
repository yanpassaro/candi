package com.brd.candi.service.redis;

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

    @Transactional
    @Scheduled(cron = "* * * 1,8,15,22,31 * *")
    public void flushAllEmpresas(){
        log.info("Deletando todos os tokens em cache");
    }
}
