package com.brd.candi.controller;

import com.brd.candi.domain.dto.EmpresaDTO;
import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.model.Response;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.EmpresaService;
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
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    final EmpresaService empresaService;
    final AuthService authService;

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> visualizar(@RequestParam("page") Integer page) {
        return ResponseEntity.ok().body(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Exibindo empresas")
                .dados(Map.of("Empresas", empresaService.visualizar(page)))
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
                .dados(Map.of("Login", authService.loginAdmin(loginDTO)))
                .build());
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public @ResponseBody ResponseEntity<Response> cadastrar(@RequestBody @Valid EmpresaDTO empresaDTO)
            throws AlreadyExistsException {
        empresaService.salvar(empresaDTO);
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(CREATED).statusCode(CREATED.value())
                .mensagem("Cadastro realizado com sucesso")
                .build());
    }

    @PutMapping("/atualizar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> atualizar(@RequestBody @Valid EmpresaDTO empresaDTO, @RequestParam("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        empresaService.atualizar(empresaDTO, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Atualização realizada com sucesso")
                .build());
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> deletar(@RequestParam("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        empresaService.deletar(tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .mensagem("Solicitação recebida com sucesso")
                .build());
    }
}
