package br.com.magalu.sbootapiwishlist.domain;

import java.util.List;

public class Wishlist {

    private String clientId;
    private List<WishlistItem> products;

    public void addProduct(WishlistItem item) {
        if (item != null && products.size() >= 20) {
            throw new IllegalStateException("Wishlist can have a maximum of 20 products.");
        }

        if (productExists(item.getProductId())) {
            throw new IllegalArgumentException("Product already exists in the wishlist.");
        }
        products.add(item);
    }

    public void removeProduct(String produtoId) {
        products.removeIf(p -> p.getProductId().equals(produtoId));
    }

    public boolean productExists(String produtoId) {
        return products.stream()
                .anyMatch(p -> p.getProductId().equals(produtoId));
    }
}
