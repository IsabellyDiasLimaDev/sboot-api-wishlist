package br.com.magalu.sbootapiwishlist.application.port.inbound;

import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;

import java.util.Optional;
import java.util.UUID;

public interface WishlistUseCase {
    WishlistItem addItem(UUID clientId, WishlistItem newItem);

    void removeItem(UUID clientId, UUID productId);

    boolean isProductInWishlist(UUID clientId, UUID productId);

    Optional<Wishlist> getByClientId(UUID clientId);
}
