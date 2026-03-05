package org.javay.marketcli.model;

public class Produto {
    private String setor;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String setor, String nome, double preco, int quantidade) {
        this.setor = setor;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getSetor() {
        return setor;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto: " + nome + " | Preço: R$ " + preco + " | Estoque: " + quantidade;
    }
}
