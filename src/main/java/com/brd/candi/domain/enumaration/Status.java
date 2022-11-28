package com.brd.candi.domain.enumaration;

public enum Status {
    APROVADO("Aprovado"),
    PENDENTE("Pendente"),
    REPROVADO("Reprovado");

    final String statusNome;

    Status(String statusNome) {
        this.statusNome = statusNome;
    }

    public String getStatusNome() {
        return statusNome;
    }
}
