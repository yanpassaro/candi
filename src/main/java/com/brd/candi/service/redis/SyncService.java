package com.brd.candi.service.redis;

import com.brd.candi.repository.EmpresaRepository;
import com.brd.candi.repository.VagaRepository;
import com.brd.candi.repository.redis.EmpresaRedisRepository;
import com.brd.candi.repository.redis.VagaRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class SyncService {
    final VagaRedisRepository vagaRedisRepository;
    final VagaRepository vagaRepository;
    final EmpresaRedisRepository empresaRedisRepository;
    final EmpresaRepository empresaRepository;
}
