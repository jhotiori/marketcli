package org.javay.marketcli.model;

import org.javay.marketcli.enums.Cargo;

public class Usuario {
    private String nome;
    private Cargo cargo;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getNome() {
        return this.nome;
    }

    public Cargo getCargo() {
        return this.cargo;
    }

    @Override
    public String toString() {
        return String.format(
            "Usuario(Nome = %s, Cargo = %s)",
            this.getNome(),
            this.getCargo()
        );
    }
}
