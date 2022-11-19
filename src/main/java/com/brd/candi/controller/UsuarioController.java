package com.brd.candi.controller;

import com.brd.candi.model.dto.UsuarioDTO;
import com.brd.candi.model.redis.UsuarioRedis;
import com.brd.candi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {
    final UsuarioService usuarioService;

    @GetMapping ("/visualizar")
    @ResponseStatus(CREATED)
    public ResponseEntity<Iterable<UsuarioRedis>> visualizar(){
        return ResponseEntity.ok(usuarioService.visualizarTodos());
    }
    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public ResponseEntity<UsuarioRedis> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvar(usuarioDTO));
    }
}
