package com.brd.candi.controller;

import com.brd.candi.repository.redis.EmpresaRedisRepository;
import com.brd.candi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmpresaController {
    final EmpresaService empresaService;
}
