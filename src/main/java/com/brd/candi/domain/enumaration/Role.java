package com.brd.candi.domain.enumaration;

public enum Role {
    ADMIN("Administrador"),
    USUARIO("Usuário"),
    RECRUTADOR("Recrutador");

    final String roleNome;

    Role(String roleNome) {
        this.roleNome = roleNome;
    }

    public String getRoleNome() {
        return roleNome;
    }
}
