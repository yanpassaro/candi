package com.brd.candi.controller;

import com.brd.candi.domain.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

    @GetMapping("/")
    @ResponseStatus(OK)
    public @ResponseBody ResponseEntity<Response> ola() {
        return ResponseEntity.ok(Response.builder()
                .data(now())
                .status(OK).statusCode(OK.value())
                .devMensagem("Está realmente tudo ok :b")
                .mensagem("Parece que está tudo ok...")
                .build());
    }

}
