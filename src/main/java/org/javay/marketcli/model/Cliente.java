package org.javay.marketcli.model;

import org.javay.marketcli.enums.Genero;

public class Cliente extends Usuario {
    private int idade;
    private Genero genero;

    public Cliente(String nome, int idade, Genero genero) {
        super(nome);
        this.idade = idade;
        this.genero = genero;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return this.idade;
    }

    public Genero getGenero() {
        return this.genero;
    }

    @Override
    public String toString() {
        return String.format(
            "Cliente(Nome = %s, Idade = %d, Genero = %s)",
            this.getNome(),
            this.getIdade(),
            this.getGenero()
        );
    }
}
