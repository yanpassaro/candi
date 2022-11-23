package com.brd.candi.controller;

import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.UsuarioService;
import com.brd.candi.service.redis.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/candidato")
@RequiredArgsConstructor
public class UsuarioController {
    final UsuarioService usuarioService;
    final AuthService authService;

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> visualizar(@RequestHeader("id") UUID id, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        authService.verificar(id, token);
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Exibindo usuário")
                .dados(Map.of("Usuário", usuarioService.visualizar(id)))
                .build());
    }

    @GetMapping(path = "/login")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> login(@RequestBody @Valid LoginDTO loginDTO)
            throws NotExistException {
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Login efetuado com sucesso")
                .dados(Map.of("Login", authService.login(loginDTO)))
                .build());
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public @ResponseBody ResponseEntity<Response> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO)
            throws AlreadyExistsException {
        usuarioService.cadastrar(usuarioDTO);
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(CREATED).statusCode(CREATED.value())
                .mensagem("Cadastro realizado com sucesso")
                .build());
    }

    @PutMapping ("/atualizar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> atualizar(@RequestBody @Valid UsuarioDTO usuarioDTO, @RequestHeader("id") UUID id, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        authService.verificar(id, token);
        usuarioService.atualizar(usuarioDTO, id);
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Atualização realizada com sucesso")
                .build());
    }

    @DeleteMapping ("/deletar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> deletar(@RequestHeader("id") UUID id, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        authService.verificar(id, token);
        usuarioService.deletar(id);
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Solicitação recebida com sucesso")
                .build());
    }
}
