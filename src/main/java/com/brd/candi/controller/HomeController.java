package com.brd.candi.controller;

import com.brd.candi.domain.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping("/")
    @ResponseStatus(OK)
    public ResponseEntity<Response<Object>> ola() {
        return ResponseEntity.ok(Response.builder()
                .date(now())
                .status(OK).statusCode(OK.value())
                .devMessage("On")
                .build());
    }

}
