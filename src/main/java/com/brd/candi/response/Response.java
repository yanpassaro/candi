package com.brd.candi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public class Response {
    private LocalDateTime data;
    private Integer statusCode;
    private HttpStatus status;
    @JsonProperty("razão")
    private String devMensagem;
    private String mensagem;
    private Map<?, ?> dados;
}
