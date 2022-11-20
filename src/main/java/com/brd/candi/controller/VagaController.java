package com.brd.candi.controller;

import com.brd.candi.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VagaController {
    final VagaService vagaService;
}
