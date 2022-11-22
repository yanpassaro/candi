package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class Response {
    private LocalDate data;
    private Integer statusCode;
    private HttpStatus status;
    private String mensagem;
    private String devMensagem;
    private Map<?, ?> dados;
}
