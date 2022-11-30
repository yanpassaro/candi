package com.brd.candi.controller;

import com.brd.candi.domain.dto.EmpresaDTO;
import com.brd.candi.domain.dto.LoginDTO;
import com.brd.candi.domain.enumaration.Status;
import com.brd.candi.domain.model.Response;
import com.brd.candi.domain.redis.TokenRedis;
import com.brd.candi.exception.custom.IncorrectCedentialsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotExistException;
import com.brd.candi.service.AnaliseService;
import com.brd.candi.service.EmpresaService;
import com.brd.candi.service.redis.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpresaController {
    final EmpresaService empresaService;
    final AuthService authService;
    final AnaliseService analiseService;

    @GetMapping("/visualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> visualizar(@DefaultValue("0") @RequestParam("page") Integer page) {
        return ResponseEntity.ok().body(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo empresas")
                .datas(empresaService.visualizar(page))
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
                .data(authService.loginAdmin(loginDTO))
                .build());
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public ResponseEntity<Response<Object>> cadastrar(@RequestBody @Valid EmpresaDTO empresaDTO)
            throws IncorrectCedentialsException {
        empresaService.salvar(empresaDTO);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Cadastro realizado com sucesso")
                .build());
    }

    @PutMapping("/atualizar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> atualizar(@RequestBody @Valid EmpresaDTO empresaDTO, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        empresaService.atualizar(empresaDTO, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Atualização realizada com sucesso")
                .build());
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> deletar(@RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        empresaService.deletar(tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Solicitação recebida com sucesso")
                .build());
    }

    @GetMapping("/detalhar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> detalhar(@RequestHeader("token") UUID token)
            throws NotAuthorizedException, NotExistException {
        TokenRedis tokenRedis = authService.autenticar(token);
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .message("Exibindo perfil da empresa")
                .data(empresaService.detalhar(tokenRedis.getIdUser()))
                .build());
    }

    @PutMapping ("/analisar")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> cadastrar(@RequestBody Status status, @RequestParam("id") UUID id, @RequestHeader("token") UUID token)
            throws NotAuthorizedException {
        TokenRedis tokenRedis = authService.autenticar(token);
        analiseService.analisar(status, id, tokenRedis.getIdUser());
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(CREATED).statusCode(CREATED.value())
                .message("Cadastrado com sucesso")
                .build());
    }
}
