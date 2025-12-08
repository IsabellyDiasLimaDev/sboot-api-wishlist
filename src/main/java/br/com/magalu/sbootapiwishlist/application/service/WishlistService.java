package br.com.magalu.sbootapiwishlist.application.service;

import br.com.magalu.sbootapiwishlist.application.port.inbound.WishlistUseCase;
import br.com.magalu.sbootapiwishlist.application.port.outbound.WishlistRepositoryPort;
import br.com.magalu.sbootapiwishlist.domain.exception.ProductAlreadyInWishlistException;
import br.com.magalu.sbootapiwishlist.domain.exception.WishlistNotFoundException;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService implements WishlistUseCase {

    private final WishlistRepositoryPort wishlistRepositoryPort;

    public WishlistService(WishlistRepositoryPort wishlistRepositoryPort) {
        this.wishlistRepositoryPort = wishlistRepositoryPort;
    }

    @Override
    public WishlistItem addItem(UUID clientId, WishlistItem newItem) {
        Wishlist wishlist = wishlistRepositoryPort.findByClientId(clientId)
                .orElseGet(() -> new Wishlist(clientId));

        boolean alreadyExists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(newItem.getProductId()));

        if (alreadyExists) {
            throw new ProductAlreadyInWishlistException("Produto já existe na wishlist");
        }

        wishlist.addProduct(newItem);
        wishlistRepositoryPort.save(wishlist);
        return newItem;
    }

    @Override
    public void removeItem(UUID clientId, UUID productId) {
        Wishlist wishlist = wishlistRepositoryPort.findByClientId(clientId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist não encontrada"));

        wishlist.removeProduct(productId);
        wishlistRepositoryPort.save(wishlist);
    }

    @Override
    public boolean isProductInWishlist(UUID clientId, UUID productId) {
        return wishlistRepositoryPort.findByClientId(clientId)
                .map(w -> w.productExists(productId))
                .orElse(false);
    }

    @Override
    public Optional<Wishlist> getByClientId(UUID clientId) {
        return wishlistRepositoryPort.findByClientId(clientId);
    }
}