package com.brd.candi.controller;

import com.brd.candi.repository.redis.EmpresaRedisRepository;
import com.brd.candi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmpresaController {
    final EmpresaService empresaService;
    final EmpresaRedisRepository empresaRedisRepository;

    @GetMapping("/hello")
    @ResponseStatus(CREATED)
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Ola mundo");
    }
}
