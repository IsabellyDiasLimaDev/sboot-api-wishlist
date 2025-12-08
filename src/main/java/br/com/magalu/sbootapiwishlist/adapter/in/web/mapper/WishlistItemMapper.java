package br.com.magalu.sbootapiwishlist.adapter.in.web.mapper;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.AddWishlistItemRequest;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;

public final class WishlistItemMapper {

    private WishlistItemMapper() {
    }

    public static WishlistItem toDomain(AddWishlistItemRequest request) {
        return new WishlistItem(
                request.getProductId(),
                request.getProductName(),
                request.getProductDescription(),
                request.getProductImageUrl(),
                request.getProductPrice(),
                request.getProductCategory()
        );
    }
}
