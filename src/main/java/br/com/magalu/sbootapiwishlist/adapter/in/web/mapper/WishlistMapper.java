package br.com.magalu.sbootapiwishlist.adapter.in.web.mapper;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.WishlistResponse;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;

import java.util.stream.Collectors;

public final class WishlistMapper {

    private WishlistMapper() {
    }

    public static WishlistResponse toResponse(Wishlist wishlist) {
        WishlistResponse response = new WishlistResponse();
        response.setClientId(wishlist.getClientId());
        response.setItems(
                wishlist.getItems()
                        .stream()
                        .map(item -> {
                            WishlistResponse.ItemResponse itemResponse =
                                    new WishlistResponse.ItemResponse();
                            itemResponse.setProductId(item.getProductId());
                            itemResponse.setProductName(item.getProductName());
                            itemResponse.setProductImageUrl(item.getProductImageUrl());
                            itemResponse.setProductPrice(item.getProductPrice());
                            return itemResponse;
                        })
                        .collect(Collectors.toList())
        );
        return response;
    }
}
