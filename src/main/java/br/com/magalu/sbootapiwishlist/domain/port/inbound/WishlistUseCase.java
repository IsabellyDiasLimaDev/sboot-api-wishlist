package br.com.magalu.sbootapiwishlist.domain.port.inbound;

import br.com.magalu.sbootapiwishlist.domain.WishlistItem;

import java.util.List;

public interface WishlistUseCase {
    void addItem(String userId, WishlistItem item);

    List<WishlistItem> getWishlist(String userId);

    void removeItem(String userId, String productId);

    boolean isProductInWishlist(String userId, String productId);
}
