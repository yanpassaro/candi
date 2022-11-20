package com.brd.candi.http;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class Request {
    @NotNull
    private UUID id;
    @NotNull
    private UUID token;
}
