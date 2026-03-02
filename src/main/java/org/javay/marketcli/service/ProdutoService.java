package org.javay.marketcli.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.javay.marketcli.exception.ValidationException;
import org.javay.marketcli.model.Produto;
import org.javay.marketcli.repository.ProdutoRepository;
import org.javay.marketcli.util.Constants;

/**
 * Service layer for managing {@link Produto} entities.
 * <p>Enforces business rules before delegating persistence operations
 * to {@link ProdutoRepository}. All write operations validate input
 * prior to repository interaction.</p>
 * <p>This class is the primary entry point for product-related operations
 * in the application layer.</p>
 * @see ProdutoRepository
 */
public class ProdutoService {
    private final ProdutoRepository repository;

    /**
     * Constructs a {@code ProdutoService} backed by the given repository.
     * @param repository the persistence layer for products; must not be {@code null}.
     */
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new product to the repository after validating its fields.
     * <p>A product is only added if no other product with the same name
     * already exists (name is the unique key).</p>
     * @param produto the product to add; name, price and stock must be valid.
     * @return {@code true} if the product was added; {@code false} if a product
     *  with the same name already exists.
     * @throws ValidationException if any field fails validation.
     * @see #remove(String)
     */
    public boolean add(Produto produto) {
        validateNome(produto.getNome());
        validatePreco(produto.getPreco());
        validateEstoque(produto.getEstoque());
        return this.repository.add(produto);
    }

    /**
     * Removes the product identified by the given name.
     * @param nome the product name to remove; must not be blank.
     * @return {@code true} if the product was found and removed; {@code false} otherwise.
     * @throws ValidationException if {@code nome} is null or blank.
     */
    public boolean remove(String nome) {
        validateNome(nome);
        return this.repository.remove(nome);
    }

    /**
     * Checks whether a product with the given name exists.
     * @param nome the product name to look up; must not be blank.
     * @return {@code true} if the product exists; {@code false} otherwise.
     * @throws ValidationException if {@code nome} is null or blank.
     */
    public boolean has(String nome) {
        validateNome(nome);
        return this.repository.has(nome);
    }

    /**
     * Updates the product identified by the given name.
     * @param nome the product name to update; must not be blank.
     * @param produto the new product; name, price and stock must be valid.
     * @throws ValidationException if any field fails validation.
     */
    public void update(String nome, Produto produto) {
        validateNome(nome);
        validatePreco(produto.getPreco());
        validateEstoque(produto.getEstoque());
        this.repository.update(nome, produto);
    }

    /**
     * Retrieves a product by its name.
     * @param nome the product name; must not be blank.
     * @return the matching {@link Produto}, or {@code null} if not found.
     * @throws ValidationException if {@code nome} is null or blank.
     */
    public Produto getByNome(String nome) {
        validateNome(nome);
        return this.repository.getByNome(nome);
    }

    /**
     * Returns all products matching the given predicate.
     * <p>Returns an empty list if no products match — never {@code null}.</p>
     * @param filtro the filter predicate to apply; must not be {@code null}.
     * @return an immutable list of matching products.
     */
    public List<Produto> getByFilter(Predicate<Produto> filtro) {
        return this.repository.getByFilter(filtro);
    }

    /**
     * Returns all registered products.
     * <p>The returned collection reflects the current state and is unordered.</p>
     * @return a collection of all products; may be empty, never {@code null}.
     */
    public Collection<Produto> getAll() {
        return this.repository.getAll();
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) throw new ValidationException(Constants.Mensagens.NOME_INVALIDO);
    }

    private void validatePreco(double preco) {
        if (preco <= 0) throw new ValidationException(Constants.Mensagens.PRECO_INVALIDO);
    }

    private void validateEstoque(int estoque) {
        if (estoque < 0) throw new ValidationException(Constants.Mensagens.ESTOQUE_INVALIDO);
    }
}
