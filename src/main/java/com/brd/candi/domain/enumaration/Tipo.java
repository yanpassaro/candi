package com.brd.candi.domain.enumaration;

public enum Tipo {
    ESTAGIO("Estágio"),
    MEIO_PERIODO("Meio período"),
    INTEGRAL("Integral"),
    VOLUNTARIO("Voluntario");

    final String tipoNome;

    Tipo(String tipoNome) {
        this.tipoNome = tipoNome;
    }
}
