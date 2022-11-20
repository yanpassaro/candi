package com.brd.candi.service;

import com.brd.candi.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaService {
    final EmpresaRepository empresaRepository;
}
