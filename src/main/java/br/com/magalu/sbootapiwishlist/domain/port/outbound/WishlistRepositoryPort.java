package br.com.magalu.sbootapiwishlist.domain.port.outbound;


import br.com.magalu.sbootapiwishlist.domain.WishlistItem;

import java.util.List;

public interface WishlistRepositoryPort {

    void addProductToWishlist(String clientId, String productId);
    void removeProductFromWishlist(String clientId, String productId);
    List<WishlistItem> getWishlist(String clientId);
    boolean existsProductInWishlist(String clientId, String productId);
}
