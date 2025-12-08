package br.com.magalu.sbootapiwishlist.adapter.in.web.mapper;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.WishlistResponse;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WishlistMapperTest {

    @Test
    @DisplayName("toResponse deve mapear Wishlist com itens para WishlistResponse")
    void toResponseShouldMapWishlistWithItems() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);
        WishlistItem item = new WishlistItem(
                productId,
                "Produto",
                "Descricao",
                "img-url",
                10.0,
                "categoria"
        );
        wishlist.addProduct(item);

        WishlistResponse response = WishlistMapper.toResponse(wishlist);

        assertNotNull(response);
        assertEquals(clientId, response.getClientId());
        assertNotNull(response.getItems());
        assertEquals(1, response.getItems().size());

        WishlistResponse.ItemResponse itemResponse = response.getItems().getFirst();
        assertEquals(productId, itemResponse.getProductId());
        assertEquals("Produto", itemResponse.getProductName());
        assertEquals("img-url", itemResponse.getProductImageUrl());
        assertEquals(10.0, itemResponse.getProductPrice());
    }

    @Test
    @DisplayName("toResponse deve mapear Wishlist vazia para WishlistResponse com lista vazia")
    void toResponseShouldMapEmptyWishlist() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);

        WishlistResponse response = WishlistMapper.toResponse(wishlist);

        assertNotNull(response);
        assertEquals(clientId, response.getClientId());
        assertNotNull(response.getItems());
        assertTrue(response.getItems().isEmpty());
    }
}

