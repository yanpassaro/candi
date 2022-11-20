package com.brd.candi.service;

import com.brd.candi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VagaService {
    final VagaRepository vagaRepository;
}
