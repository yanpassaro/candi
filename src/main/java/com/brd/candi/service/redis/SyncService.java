package com.brd.candi.service.redis;

import com.brd.candi.repository.redis.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class SyncService {
    final TokenRepository tokenRepository;

    @Transactional
    @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.DAYS)
    public void flushAllEmpresas(){
        log.info("Deletando todos os tokens em cache");
        tokenRepository.deleteAll();
    }
}
