package br.com.magalu.sbootapiwishlist.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wishlist {

    private final UUID clientId;
    private final List<WishlistItem> products;

    public Wishlist(UUID clientId) {
        this.clientId = clientId;
        this.products = new ArrayList<>();
    }

    public void addProduct(WishlistItem item) {
        if (item != null && products.size() >= 20) {
            throw new IllegalStateException("Wishlist can have a maximum of 20 products.");
        }

        if (productExists(item.getProductId())) {
            throw new IllegalArgumentException("Product already exists in the wishlist.");
        }
        products.add(item);
    }

    public void removeProduct(UUID produtoId) {
        products.removeIf(p -> p.getProductId().equals(produtoId));
    }

    public boolean productExists(UUID produtoId) {
        return products.stream()
                .anyMatch(p -> p.getProductId().equals(produtoId));
    }

    public List<WishlistItem> getItems() {
        return new ArrayList<>(products);
    }

    public UUID getClientId() {
        return clientId;
    }
}
