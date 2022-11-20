package com.brd.candi.controller;

import com.brd.candi.model.redis.EmpresaRedis;
import com.brd.candi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {
    final UsuarioService usuarioService;

    @GetMapping("/visualizar")
    @ResponseStatus(CREATED)
    public ResponseEntity<EmpresaRedis> visualizar() {
        return ResponseEntity.ok(EmpresaRedis.builder().id(UUID.randomUUID()).build());
    }
}
