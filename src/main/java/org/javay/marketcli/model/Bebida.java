package org.javay.marketcli.model;

public class Bebida extends Produto{
    private boolean isAlcoolica;
    private int ml;
    private String marca;

    public Bebida(String setor, String nome, double preco, int quantidade, boolean isAlcoolica, int ml, String marca) {
        super(setor, nome, preco, quantidade);
        this.isAlcoolica = isAlcoolica;
        this.ml = ml;
        this.marca = marca;

        if (ml <= 0) {
            throw new IllegalArgumentException("O ml das bebidas devem ser maior do que 0.");
        }

        this.isAlcoolica = isAlcoolica;
        this.marca = marca;
        this.ml = ml;
    }

    public boolean isAlcoolica() {
        return isAlcoolica;
    }

    public void setAlcoolica(boolean alcoolica) {
        isAlcoolica = alcoolica;
    }

    public int getMl() {
        return ml;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        String info;

        if (isAlcoolica) {
            info = "Bebida Alcoólica";
        } else {
            info = "Bebida sem Alcool";
        }

        return info + " da marca " + marca + " com " + ml + "ml";
    }
}
