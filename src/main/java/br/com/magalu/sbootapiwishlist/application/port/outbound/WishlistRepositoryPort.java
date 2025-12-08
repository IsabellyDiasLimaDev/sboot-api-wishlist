package br.com.magalu.sbootapiwishlist.application.port.outbound;


import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishlistRepositoryPort {
    Optional<Wishlist> findByClientId(UUID clientId);
    void save(Wishlist wishlist);
}
