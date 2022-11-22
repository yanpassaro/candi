package com.brd.candi.controller;

import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.dto.UsuarioDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/candidato")
@RequiredArgsConstructor
public class UsuarioController {
    final UsuarioService usuarioService;

    @GetMapping("/visualizar")
    @ResponseStatus(value = OK, code = OK)
    public ResponseEntity<Response> visualizar(@RequestParam("id") UUID id)
            throws NotExistException {
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Exibindo candidato")
                .dados(Map.of("Candidato", usuarioService.visualizar(id)))
                .build());
    }

    @GetMapping(path = "/login", produces={MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = OK, code = OK)
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDTO loginDTO)
            throws NotExistException {
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(CREATED).statusCode(CREATED.value())
                .mensagem("Login efetuado com sucesso")
                .dados(Map.of("Candidato", usuarioService.login(loginDTO)))
                .build());
    }

    @PostMapping("/salvar")
    @ResponseStatus(CREATED)
    public ResponseEntity<Response> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO)
            throws AlreadyExistsException {
        usuarioService.salvar(usuarioDTO);
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(CREATED).statusCode(CREATED.value())
                .mensagem("Cadastro realizado com sucesso!")
                .build());
    }
}
