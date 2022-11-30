package com.brd.candi.controller;

import com.brd.candi.domain.dto.AtualizarUsuarioDTO;
import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.UsuarioService;
import com.brd.candi.service.redis.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {
    final UsuarioService usuarioService;
    final AuthService authService;

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> visualizar(@RequestHeader("token") UUID token)
            throws NotAuthorizedException, NotExistException {
        TokenRedis tokenRedis = authService.autenticar(token);
        return ResponseEntity.ok().body(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo usuário")
                .data(usuarioService.visualizar(tokenRedis.getIdUser()))
                .build());
    }

    @PostMapping(path = "/login")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> login(@RequestBody @Valid LoginDTO loginDTO)
            throws NotExistException {
        return ResponseEntity.ok().body(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Login efetuado com sucesso")
                .data(authService.login(loginDTO))
                .build());
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public ResponseEntity<Response<Object>> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO)
            throws AlreadyExistsException {
        usuarioService.cadastrar(usuarioDTO);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Cadastro realizado com sucesso")
                .build());
    }

    @PutMapping("/atualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> atualizar(@RequestBody @Valid AtualizarUsuarioDTO usuarioDTO, @RequestHeader("token") UUID token)
            throws NotAuthorizedException, NotExistException {
        TokenRedis tokenRedis = authService.autenticar(token);
        usuarioService.atualizar(usuarioDTO, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Atualização realizada com sucesso")
                .build());
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> deletar(@RequestHeader("token") UUID token)
            throws NotAuthorizedException, NotExistException {
        TokenRedis tokenRedis = authService.autenticar(token);
        usuarioService.deletar(tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Solicitação recebida com sucesso")
                .build());
    }
}
