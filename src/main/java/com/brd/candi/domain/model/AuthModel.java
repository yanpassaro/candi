package com.brd.candi.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public class AuthModel {
    private UUID id;
    private UUID token;
    private String role;
    private Boolean ativo;
}
