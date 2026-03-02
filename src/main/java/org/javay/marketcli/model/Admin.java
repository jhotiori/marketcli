package org.javay.marketcli.model;

import org.javay.marketcli.enums.Cargo;

public class Admin extends Usuario {
    private final String senha;

    public Admin(String nome, String senha) {
        super(nome);
        this.senha = senha;
        this.setCargo(Cargo.ADMIN);
    }

    public String getSenha() {
        return this.senha;
    }
}
