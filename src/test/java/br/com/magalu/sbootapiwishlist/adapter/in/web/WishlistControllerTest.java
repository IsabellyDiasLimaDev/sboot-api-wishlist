package br.com.magalu.sbootapiwishlist.adapter.in.web;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.AddWishlistItemRequest;
import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.WishlistResponse;
import br.com.magalu.sbootapiwishlist.adapter.in.web.mapper.WishlistItemMapper;
import br.com.magalu.sbootapiwishlist.application.port.inbound.WishlistUseCase;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistControllerTest {

    private WishlistUseCase wishlistUseCase;
    private WishlistController controller;

    private UUID clientId;
    private UUID productId;

    @BeforeEach
    void setup() {
        wishlistUseCase = mock(WishlistUseCase.class);
        controller = new WishlistController(wishlistUseCase);

        clientId = UUID.randomUUID();
        productId = UUID.randomUUID();
    }

    // ------------------------------------------------------------
    // TESTE: ADICIONAR ITEM (Cenário Feliz)
    // ------------------------------------------------------------
    @Test
    void shouldAddItemSuccessfully() {
        AddWishlistItemRequest request = new AddWishlistItemRequest(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0

        );

        when(wishlistUseCase.addItem(eq(clientId), any(WishlistItem.class)))
                .thenReturn(WishlistItemMapper.toDomain(request));

        ResponseEntity<Void> response = controller.addItem(clientId, request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(
                "/wishlists/" + clientId + "/items/" + productId,
                response.getHeaders().getLocation().toString()
        );
    }

    // ------------------------------------------------------------
    // TESTE: ADICIONAR ITEM (Erro)
    // ------------------------------------------------------------
    @Test
    void shouldReturnErrorWhenUseCaseFailsToAddItem() {
        AddWishlistItemRequest request = new AddWishlistItemRequest(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        when(wishlistUseCase.addItem(eq(clientId), any(WishlistItem.class)))
                .thenThrow(new RuntimeException("Erro ao adicionar item"));

        assertThrows(RuntimeException.class,
                () -> controller.addItem(clientId, request));
    }

    // ------------------------------------------------------------
    // TESTE: GET WISHLIST (Feliz)
    // ------------------------------------------------------------
    @Test
    void shouldReturnWishlistSuccessfully() {
        WishlistItem item = new WishlistItem(productId,
                "Tenis Nike",
                "url.jpg",
                500.0);
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.getItems().add(item);

        when(wishlistUseCase.getByClientId(clientId))
                .thenReturn(Optional.of(wishlist));

        ResponseEntity<WishlistResponse> response = controller.getWishlist(clientId);

assertTrue(response.getStatusCode().is2xxSuccessful());
assertNotNull(response.getBody());
    }

    // ------------------------------------------------------------
    // TESTE: GET WISHLIST (Não encontrado)
    // ------------------------------------------------------------
    @Test
    void shouldReturnNotFoundWhenWishlistDoesNotExist() {
        when(wishlistUseCase.getByClientId(clientId))
                .thenReturn(Optional.empty());

        ResponseEntity<WishlistResponse> response = controller.getWishlist(clientId);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    // ------------------------------------------------------------
    // TESTE: REMOVER ITEM (Feliz)
    // ------------------------------------------------------------
    @Test
    void shouldRemoveItemSuccessfully() {
        doNothing().when(wishlistUseCase).removeItem(clientId, productId);

        ResponseEntity<Void> response = controller.removeItem(clientId, productId);

        assertEquals(204, response.getStatusCode().value());
        verify(wishlistUseCase, times(1)).removeItem(clientId, productId);
    }

    // ------------------------------------------------------------
    // TESTE: REMOVER ITEM (Erro)
    // ------------------------------------------------------------
    @Test
    void shouldReturnErrorWhenRemoveItemFails() {
        doThrow(new RuntimeException("Item não existe"))
                .when(wishlistUseCase).removeItem(clientId, productId);

        assertThrows(RuntimeException.class,
                () -> controller.removeItem(clientId, productId));
    }

    // ------------------------------------------------------------
    // TESTE: VERIFICAR EXISTÊNCIA (Feliz)
    // ------------------------------------------------------------
    @Test
    void shouldReturnTrueWhenProductExists() {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenReturn(true);

        ResponseEntity<Boolean> response = controller.isProductInWishlist(clientId, productId);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(Boolean.TRUE.equals(response.getBody()));
    }

    // ------------------------------------------------------------
    // TESTE: VERIFICAR EXISTÊNCIA (Falso)
    // ------------------------------------------------------------
    @Test
    void shouldReturnFalseWhenProductDoesNotExist() {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenReturn(false);

        ResponseEntity<Boolean> response = controller.isProductInWishlist(clientId, productId);

        assertEquals(200, response.getStatusCode().value());
        assertFalse(Boolean.TRUE.equals(response.getBody()));
    }

    // ------------------------------------------------------------
    // TESTE: VERIFICAR EXISTÊNCIA (Erro)
    // ------------------------------------------------------------
    @Test
    void shouldThrowErrorWhenUseCaseFailsOnCheck() {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenThrow(new RuntimeException("Erro no caso de uso"));

        assertThrows(RuntimeException.class,
                () -> controller.isProductInWishlist(clientId, productId));
    }
}