package org.javay.marketcli.model;

public class Comida extends Produto {
    private boolean isVegano;
    private double peso;
    private int caloria;
    private boolean temLactose;
    private boolean isAsiatica;
    private boolean isSobremesa;

    public Comida(String setor, String nome, double preco, int quantidade, boolean isVegano, double peso, int caloria, boolean temLactose, boolean isAsiatica, boolean isSobremesa) {
        super(setor, nome, preco, quantidade);

    if (peso <= 0) {
        throw new IllegalArgumentException("O peso em gramas deve ser maior que zero.");
    }
        this.isVegano = isVegano;
        this.peso = peso;
        this.caloria = caloria;
        this.temLactose = temLactose;
        this.isAsiatica = isAsiatica;
        this.isSobremesa = isSobremesa;
    }

    public boolean isVegano() {
        return isVegano;
    }

    public void setVegano(boolean vegano) {
        isVegano = vegano;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getCaloria() {
        return caloria;
    }

    public void setCaloria(int caloria) {
        this.caloria = caloria;
    }

    public boolean isTemLactose() {
        return temLactose;
    }

    public void setTemLactose(boolean temLactose) {
        this.temLactose = temLactose;
    }

    public boolean isAsiatica() {
        return isAsiatica;
    }

    public void setAsiatica(boolean asiatica) {
        isAsiatica = asiatica;
    }

    public boolean isSobremesa() {
        return isSobremesa;
    }

    public void setSobremesa(boolean sobremesa) {
        isSobremesa = sobremesa;
    }

    @Override
    public String toString() {
        // sobremesa ou comida?
        String nomeProduto = isSobremesa ? "Sobremesa" : "Comida";

        // caracteristicas
        if (isAsiatica) {
            nomeProduto += " Asiática"; // Adiciona a palavra na frente
        }

        if (isVegano) {
            nomeProduto += " Vegana";
        }

        if (!temLactose) {
            nomeProduto += " (Sem Lactose)";
        }

        return nomeProduto;
    }
}
