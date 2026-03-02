package org.javay.marketcli.model;

public class Produto {
    private String nome;
    private double preco;
    private int estoque;

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public int getEstoque() {
        return this.estoque;
    }

    @Override
    public String toString() {
        return String.format(
            "Produto(Nome = %s, Preco = %.2f, Estoque = %d)",
            this.getNome(),
            this.getPreco(),
            this.getEstoque()
        );
    }
}
