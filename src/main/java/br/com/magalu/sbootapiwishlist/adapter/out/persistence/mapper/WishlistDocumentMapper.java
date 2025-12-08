package br.com.magalu.sbootapiwishlist.adapter.out.persistence.mapper;

import br.com.magalu.sbootapiwishlist.adapter.in.web.mapper.WishlistItemMapper;
import br.com.magalu.sbootapiwishlist.adapter.in.web.mapper.WishlistMapper;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistDocument;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistItemDocument;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WishlistDocumentMapper {

    public Wishlist toDomain(WishlistDocument wishlistDocument) {
        Wishlist wishlist = new Wishlist(wishlistDocument.getClientId());
        if (wishlistDocument.getItems() != null) {
            wishlistDocument.getItems().forEach(wishlistItemDocument -> {
                WishlistItem item = new WishlistItem(
                        wishlistItemDocument.getProductId(),
                        wishlistItemDocument.getProductName(),
                        wishlistItemDocument.getProductDescription(),
                        wishlistItemDocument.getProductImageUrl(),
                        wishlistItemDocument.getProductPrice(),
                        wishlistItemDocument.getProductCategory()
                );
                wishlist.addProduct(item);
            });
        }
        return wishlist;
    }

    public WishlistDocument toDocument(Wishlist wishlist) {
        var items = wishlist.getItems() != null
                ? wishlist.getItems()
                        .stream()
                        .map(wishlistItem -> new WishlistItemDocument(
                                wishlistItem.getProductId(),
                                wishlistItem.getProductName(),
                                wishlistItem.getProductDescription(),
                                wishlistItem.getProductImageUrl(),
                                wishlistItem.getProductPrice(),
                                wishlistItem.getProductCategory()
                        ))
                        .collect(Collectors.toList())
                : null;

        return new WishlistDocument(
                wishlist.getClientId(),
                items
        );
    }
}
