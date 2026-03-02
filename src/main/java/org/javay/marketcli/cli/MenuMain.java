package org.javay.marketcli.cli;

import org.javay.marketcli.model.Produto;
import org.javay.marketcli.service.ProdutoService;
import org.javay.marketcli.util.Constants;
import org.javay.marketcli.util.InputHandler;
import org.javay.marketcli.util.Logger;

public class MenuMain extends Menu {
    private final ProdutoService service;
    private final MenuListagem listagem;

    public MenuMain(ProdutoService service, InputHandler input, MenuListagem listagem) {
        super(input);
        this.service = service;
        this.listagem = listagem;
    }

    @Override
    public String header() {
        return """
            ━━━━━━━━━━━━━━━━━━━━ MARKET (CLI) ━━━━━━━━━━━━━━━━━━━━
            [1] - Adicionar Produto
            [2] - Remover Produto
            [3] - Atualizar Produto
            [4] - Listar Produtos
            [0] - Sair
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            """;
    }

    @Override
    public void handle(int opcao) {
        switch (opcao) {
            case 0 -> System.out.println("Saindo...");
            case 1 -> handleAddProduto();
            case 2 -> handleRemoveProduto();
            case 3 -> handleUpdateProduto();
            case 4 -> handleListarProdutos();
            default -> System.out.println("Opção inválida!");
        }
    }

    private void handleAddProduto() {
        String nome = readNomeNovo("Insira o nome do Produto: ");
        double preco = this.input.readDouble("Insira o Preço do Produto: ");
        int estoque = this.input.readInt("Insira o Estoque do Produto: ");

        this.service.add(new Produto(nome, preco, estoque));
        Logger.success(String.format("Produto '%s' adicionado com sucesso!", nome));
    }

    private void handleRemoveProduto() {
        if (this.service.getAll().isEmpty()) {
            Logger.warn(Constants.Mensagens.NENHUM_PRODUTO);
            return;
        }
        String nome = readNomeExistente("Insira o nome do Produto para remover: ");
        this.service.remove(nome);
        Logger.success(String.format("Produto '%s' removido com sucesso!", nome));
    }

    private void handleUpdateProduto() {
        if (this.service.getAll().isEmpty()) {
            Logger.warn(Constants.Mensagens.NENHUM_PRODUTO);
            return;
        }

        String nome = readNomeExistente("Insira o nome do Produto para atualizar: ");
        Produto atual = this.service.getByNome(nome);
        String propriedade = this.input.readString("Insira a propriedade (nome, valor, estoque): ")
            .trim().toLowerCase();

        Produto atualizado = switch (propriedade) {
            case "nome" -> {
                String novoNome = readNomeNovo("Insira o novo nome: ");
                yield new Produto(novoNome, atual.getPreco(), atual.getEstoque());
            }
            case "valor" -> {
                double novoPreco = this.input.readDouble("Insira o novo Preço: ");
                yield new Produto(atual.getNome(), novoPreco, atual.getEstoque());
            }
            case "estoque" -> {
                int novoEstoque = this.input.readInt("Insira o novo Estoque: ");
                yield new Produto(atual.getNome(), atual.getPreco(), novoEstoque);
            }
            default -> {
                Logger.warn(Constants.Mensagens.OPCAO_INVALIDA);
                yield null;
            }
        };

        if (atualizado != null) {
            this.service.update(nome, atualizado);
            Logger.success(String.format("Produto '%s' atualizado com sucesso! (%s atualizado)", nome, propriedade));
        }
    }

    private String readNomeExistente(String prompt) {
        String nome = this.input.readString(prompt);
        while (!this.service.has(nome)) {
            nome = this.input.readString(String.format("'%s' não existe! Insira outro nome: ", nome));
        }
        return nome;
    }

    private String readNomeNovo(String prompt) {
        String nome = this.input.readString(prompt);
        while (this.service.has(nome)) {
            nome = this.input.readString(String.format("'%s' já existe! Insira outro nome: ", nome));
        }
        return nome;
    }

    private void handleListarProdutos() {
        this.listagem.show();
    }
}
