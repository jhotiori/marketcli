package org.javay.marketcli.cli;

import org.javay.marketcli.util.Constants;
import org.javay.marketcli.util.InputHandler;

public abstract class Menu {
    protected final InputHandler input;

    protected Menu(InputHandler input) {
        this.input = input;
    }

    public void show() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(header());
            opcao = this.input.readInt(Constants.Mensagens.OPCAO_PROMPT);
            handle(opcao);
        }
    }

    public abstract String header();
    public abstract void handle(int opcao);
}
