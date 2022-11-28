package com.brd.candi.controller;

import com.brd.candi.domain.dto.CandidaturaDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.CandidaturaService;
import com.brd.candi.service.redis.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/candidatura")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CandidaturaController {
    final CandidaturaService candidaturaService;
    final AuthService authService;

    @GetMapping("/visualizar/candidato")
    public ResponseEntity<Response<Object>> cadastrar(@RequestHeader("token") UUID token, @DefaultValue("0") @RequestParam("page") Integer page)
            throws NotExistException, NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        candidaturaService.visualizar(tokenRedis.getIdUser(), page);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Cadastrado com sucesso")
                .build());
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> cadastrar(@Valid @RequestBody CandidaturaDTO candidaturaDTO, @RequestHeader("token") UUID token)
            throws NotAuthorizedException, AlreadyExistsException {
        TokenRedis tokenRedis = authService.autenticar(token);
        candidaturaService.cadastrar(candidaturaDTO, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Cadastrado com sucesso")
                .build());
    }

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> visualizar(@RequestParam("id") UUID id, @RequestHeader("token") UUID token, @DefaultValue("0") @RequestParam("page") Integer page)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo Candidatos")
                .datas(candidaturaService.visualizar(id, tokenRedis.getIdUser(), page))
                .build());
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> deletar(@RequestParam("id") UUID id, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        candidaturaService.deletar(id, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Solicitação recebida com sucesso")
                .build());
    }

    @GetMapping("/detalhar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> detalhar(@RequestParam("id") UUID id, @RequestHeader("token") UUID token)
            throws NotExistException, NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo Candidato")
                .data(candidaturaService.detalhar(id, tokenRedis.getIdUser()))
                .build());
    }
}
