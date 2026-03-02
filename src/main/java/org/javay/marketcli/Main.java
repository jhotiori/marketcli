package org.javay.marketcli;

import java.util.Scanner;

import org.javay.marketcli.cli.MenuListagem;
import org.javay.marketcli.cli.MenuMain;
import org.javay.marketcli.repository.ProdutoRepository;
import org.javay.marketcli.service.ProdutoService;
import org.javay.marketcli.util.InputHandler;

public class Main {
    public static void main(String[] args) {
        ProdutoRepository repository = new ProdutoRepository();
        ProdutoService service = new ProdutoService(repository);

        InputHandler handler = new InputHandler(new Scanner(System.in));
        MenuListagem listagem = new MenuListagem(service, handler);
        MenuMain menu = new MenuMain(service, handler, listagem);
        menu.show();
    }
}
