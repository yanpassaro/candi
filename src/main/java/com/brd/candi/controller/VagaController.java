package com.brd.candi.controller;

import com.brd.candi.domain.dto.VagaDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.VagaService;
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
@RequestMapping("/api/vaga")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VagaController {
    final VagaService vagaService;
    final AuthService authService;

    @PostMapping("/cadastrar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> cadastrar(@Valid @RequestBody VagaDTO vagaDTO, @RequestParam("token") UUID token)
            throws AlreadyExistsException, NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        vagaService.cadastrar(vagaDTO, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Vaga cadastrada com sucesso")
                .build());
    }

    @GetMapping("/detalhar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> detalhar(@RequestParam("id") UUID id)
            throws NotExistException {
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo vaga desejada")
                .data(vagaService.detalhar(id))
                .build());
    }

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> visualizar(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Retornando todas as vagas")
                .datas(vagaService.visualizar(page))
                .build());
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> deletar(@RequestParam("id") UUID id, @RequestParam("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        vagaService.deletar(id, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Solicitação recebida com sucesso")
                .build());
    }
}

