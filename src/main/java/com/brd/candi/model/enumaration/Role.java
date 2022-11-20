package com.brd.candi.model.enumaration;

public enum Role {
    OWNER("Administrador"),
    CANDIDATO("Candidato"),
    RECRUTADOR("Recrutador");

    final String roleNome;

    Role(String roleNome) {
        this.roleNome = roleNome;
    }

    public String getRoleNome() {
        return roleNome;
    }
}