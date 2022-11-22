package com.brd.candi.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class Response {
    private LocalDateTime data;
    private Integer statusCode;
    private HttpStatus status;
    private String devMensagem;
    private String mensagem;
    private Map<?, ?> dados;
}
