package br.com.magalu.sbootapiwishlist.adapter.out.persistence.mapper;

import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistDocument;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistItemDocument;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WishlistDocumentMapperTest {

    private final WishlistDocumentMapper mapper = new WishlistDocumentMapper();

    @Test
    @DisplayName("toDomain deve mapear WishlistDocument com itens para Wishlist de dominio")
    void toDomainShouldMapDocumentWithItems() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        WishlistItemDocument itemDoc = new WishlistItemDocument(
                productId,
                "Produto",
                "Descricao",
                "img-url",
                10.0,
                "categoria"
        );

        WishlistDocument document = new WishlistDocument(clientId, List.of(itemDoc));

        Wishlist result = mapper.toDomain(document);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertEquals(1, result.getItems().size());
        WishlistItem item = result.getItems().getFirst();
        assertEquals(productId, item.getProductId());
        assertEquals("Produto", item.getProductName());
        assertEquals("Descricao", item.getProductDescription());
        assertEquals("img-url", item.getProductImageUrl());
        assertEquals(10.0, item.getProductPrice());
        assertEquals("categoria", item.getProductCategory());
    }

    @Test
    @DisplayName("toDomain deve lidar com lista de itens nula retornando Wishlist vazia")
    void toDomainShouldHandleNullItemsList() {
        UUID clientId = UUID.randomUUID();
        WishlistDocument document = new WishlistDocument(clientId, null);

        Wishlist result = mapper.toDomain(document);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertNotNull(result.getItems());
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    @DisplayName("toDocument deve mapear Wishlist de dominio com itens para WishlistDocument")
    void toDocumentShouldMapDomainWithItems() {
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

        WishlistDocument document = mapper.toDocument(wishlist);

        assertNotNull(document);
        assertEquals(clientId, document.getClientId());
        assertNotNull(document.getItems());
        assertEquals(1, document.getItems().size());
        WishlistItemDocument itemDoc = document.getItems().getFirst();
        assertEquals(productId, itemDoc.getProductId());
        assertEquals("Produto", itemDoc.getProductName());
        assertEquals("Descricao", itemDoc.getProductDescription());
        assertEquals("img-url", itemDoc.getProductImageUrl());
        assertEquals(10.0, itemDoc.getProductPrice());
        assertEquals("categoria", itemDoc.getProductCategory());
    }

    @Test
    @DisplayName("toDocument deve lidar com lista de itens nula ou vazia")
    void toDocumentShouldHandleNullOrEmptyItems() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);

        WishlistDocument document = mapper.toDocument(wishlist);

        assertNotNull(document);
        assertEquals(clientId, document.getClientId());
        assertNotNull(document.getItems());
        assertTrue(document.getItems().isEmpty());
    }
}

