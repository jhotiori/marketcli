package org.javay.marketcli.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.javay.marketcli.model.Produto;

public class ProdutoService {
    private final Map<String, Produto> produtos = new HashMap<>();

    /*
     * Adiciona um novo Produto ao serviço.
     * @param produto - O Produto para adicionar
     * @throws IllegalArgumentException
    */
    public void addProduto(Produto produto) {
        validateProduto(produto);
        this.produtos.putIfAbsent(produto.getNome(), produto);
    }

    /*
     * Remove um Produto do serviço.
     * @param nome - Nome do produto para ser removido
    */
    public void removeProduto(String nome) {
        this.produtos.remove(nome);
    }

    /*
     * Atualiza um Produto no serviço.
     * @param nome - Nome do produto para ser atualizado
     * @param updated - O Produto atualizado
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
    */
    public void updateProduto(String nome, Produto updated) {
        if (!hasProduto(nome)) {
            throw new NoSuchElementException(String.format("Produto %s não encontrado!", nome));
        }
        validateProduto(updated);
        this.produtos.put(nome, updated);
    }

    /*
     * Verifica se um Produto existe no serviço.
     * @param nome - Nome do produto a ser verificado
     * @return true se o Produto existir, false caso contrário
    */
    public boolean hasProduto(String nome) {
        return this.produtos.containsKey(nome);
    }

    /*
     * Obtem todos os Produtos do serviço.
     * @return Todos os Produtos
    */
    public Collection<Produto> getAllProdutos() {
        return this.produtos.values();
    }

    /*
     * Acha um produto pelo seu nome
     * @param nome - O nome do produto para achar
     * @return O produto encontrado
    */
    public Produto findProdutoByNome(String nome) {
        return this.produtos.get(nome);
    }

    /*
     * Procura um Produto por um filtro.
     * @param filter - O filtro para procurar
     * @return O Produto encontrado
    */
    public Produto findProdutoByFilter(Predicate<Produto> filter) {
        return this.produtos.values().stream().filter(filter).findFirst().orElse(null);
    }

    /*
     * Procura Produtos por um filtro.
     * @param filter - O filtro para procurar
     * @return Os Produtos encontrados
    */
    public Collection<Produto> findProdutosByFilter(Predicate<Produto> filter) {
        return this.produtos.values().stream().filter(filter).toList();
    }

    /*
     * Valida um Produto.
     * @param produto - O Produto para ser validado
    */
    private void validateProduto(Produto produto) {
        String setor = produto.getSetor();
        if (setor == null || setor.isBlank()) {
            throw new IllegalArgumentException("O setor do produto não pode ser nulo ou vazio!");
        }

        String nome = produto.getNome();
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio!");
        }

        double preco = produto.getPreco();
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto precisa ser maior que zero!");
        }

        int quantidade = produto.getQuantidade();
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade do produto precisa ser maior ou igual a zero!");
        }
    }
}
