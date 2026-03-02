package org.javay.marketcli.cli;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.javay.marketcli.model.Produto;
import org.javay.marketcli.service.ProdutoService;
import org.javay.marketcli.util.Constants.Mensagens;
import org.javay.marketcli.util.InputHandler;
import org.javay.marketcli.util.Logger;

public class MenuListagem extends Menu {
    private final ProdutoService service;

    public MenuListagem(ProdutoService service, InputHandler input) {
        super(input);
        this.service = service;
    }

    @Override
    public String header() {
        return """
            ━━━━━━━━━━━━━━━━━━━━ MARKET (CLI) ━━━━━━━━━━━━━━━━━━━━
            [1] - Listar todos os Produtos
            [2] - Listar Produtos esgotatos
            [3] - Listar Produtos em estoque
            [0] - Sair
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            """;
    }

    @Override
    public void handle(int opcao) {
        switch (opcao) {
            case 0 -> {}
            case 1 -> handleListar("━━━━━━━━━━━━━━━━━━━━ PRODUTOS ━━━━━━━━━━━━━━━━━━━━━━━━", p -> true, Mensagens.NENHUM_PRODUTO);
            case 2 -> handleListar("━━━━━━━━━━━━━━━━━━━━ PRODUTOS (ESGOTADOS) ━━━━━━━━━━━━", p -> p.getEstoque() == 0, Mensagens.NENHUM_ESGOTADO);
            case 3 -> handleListar("━━━━━━━━━━━━━━━━━━━━ PRODUTOS (COM ESTOQUE) ━━━━━━━━━━", p -> p.getEstoque() > 0, Mensagens.NENHUM_EM_ESTOQUE);
            default -> Logger.warn(Mensagens.OPCAO_INVALIDA);
        }
    }

    private void handleListar(String titulo, Predicate<Produto> filtro, String mensagem) {
        Collection<Produto> produtos = this.service.getByFilter(filtro);

        if (produtos.isEmpty()) {
            Logger.warn(mensagem);
            return;
        }

        System.out.println(String.format(
            """
            %s
            %s
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━""",
            titulo,
            produtos.stream()
                .map(Produto::toString)
                .map(String::strip)
                .collect(Collectors.joining("\n"))
        ));
    }
}
