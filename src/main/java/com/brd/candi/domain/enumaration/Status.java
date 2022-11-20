package com.brd.candi.domain.enumaration;

public enum Status {
    APROVADO("Parabéns!!! você passou para a próxima fase do processo seletivo"),
    REPROVADO("Infelizmente não foi dessa vez :(");

    final String statusNome;

    Status(String statusNome) {
        this.statusNome = statusNome;
    }

    public String getStatusNome() {
        return statusNome;
    }
}
