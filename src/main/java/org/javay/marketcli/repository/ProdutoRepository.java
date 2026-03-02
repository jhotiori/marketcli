package org.javay.marketcli.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.javay.marketcli.model.Produto;

/**
 * In-memory repository for {@link Produto} entities.
 * <p>Products are indexed by name, names are treated as unique identifiers.
 * All operations are O(1) for single-key lookups, backed by a {@link HashMap}.</p>
 * <p><strong>Note:</strong> This implementation is not thread-safe and does not
 * persist data between application runs.</p>
 * @see org.javay.marketcli.service.ProdutoService
 */
public class ProdutoRepository {
    private final Map<String, Produto> produtos = new HashMap<>();

    /**
     * Adds a product to the repository if no product with the same name exists.
     * @param produto the product to add; its name is used as the unique key.
     * @return {@code true} if inserted; {@code false} if the name was already present.
     */
    public boolean add(Produto produto) {
        return this.produtos.putIfAbsent(produto.getNome(), produto) == null;
    }

    /**
     * Removes the product associated with the given name.
     * @param nome the name key to remove.
     * @return {@code true} if a product was removed; {@code false} if not found.
     */
    public boolean remove(String nome) {
        return this.produtos.remove(nome) != null;
    }

    /**
     * Checks whether a product with the given name is stored.
     * @param nome the name to look up.
     * @return {@code true} if present; {@code false} otherwise.
     */
    public boolean has(String nome) {
        return this.produtos.containsKey(nome);
    }

    /**
     * Updates the product associated with the given name.
     * @param nome the name to update.
     * @param produto the new produto.
     */
    public void update(String nome, Produto produto) {
        this.produtos.put(nome, produto);
    }

    /**
     * Retrieves the product associated with the given name.
     * @param nome the name to look up.
     * @return the corresponding {@link Produto}, or {@code null} if not found.
     */
    public Produto getByNome(String nome) {
        return this.produtos.get(nome);
    }

    /**
     * Returns all products that satisfy the given predicate.
     * <p>Streams the full collection and filters lazily, for large datasets,
     * consider indexed alternatives.</p>
     * @param filtro the predicate to test each product against.
     * @return an immutable list of matching products; empty if none match.
     */
    public List<Produto> getByFilter(Predicate<Produto> filtro) {
        return this.getAll()
            .stream()
            .filter(filtro)
            .toList();
    }

    /**
     * Returns all stored products as an unordered collection.
     * <p>The returned collection is a live view, changes to the repository
     * will be reflected in it.</p>
     * @return a collection of all products; may be empty, never {@code null}.
     */
    public Collection<Produto> getAll() {
        return this.produtos.values();
    }
}
