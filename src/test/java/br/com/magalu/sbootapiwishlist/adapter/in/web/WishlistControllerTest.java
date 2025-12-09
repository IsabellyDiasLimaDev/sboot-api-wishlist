package br.com.magalu.sbootapiwishlist.adapter.in.web;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.AddWishlistItemRequest;
import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.WishlistResponse;
import br.com.magalu.sbootapiwishlist.application.port.inbound.WishlistUseCase;
import br.com.magalu.sbootapiwishlist.domain.exception.ProductAlreadyInWishlistException;
import br.com.magalu.sbootapiwishlist.domain.exception.WishlistNotFoundException;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
@AutoConfigureMockMvc(addFilters = false)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistUseCase wishlistUseCase;

    private final UUID clientId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private final UUID productId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    // ------------------------------------------------------------
    // POST /wishlists/{clientId}/items - cenário feliz
    // ------------------------------------------------------------
    @Test
    void shouldAddItemSuccessfully() throws Exception {
        AddWishlistItemRequest request = new AddWishlistItemRequest(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        WishlistItem created = new WishlistItem(
                productId,
                request.getProductName(),
                request.getProductImageUrl(),
                request.getProductPrice()
        );

        when(wishlistUseCase.addItem(eq(clientId), any(WishlistItem.class)))
                .thenReturn(created);

        String requestJson = """
                {
                  "productId": "%s",
                  "productName": "%s",
                  "productImageUrl": "%s",
                  "productPrice": %s
                }
                """.formatted(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        mockMvc.perform(post("/wishlists/{clientId}/items", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    // ------------------------------------------------------------
    // POST /wishlists/{clientId}/items - produto já existe (409)
    // ------------------------------------------------------------
    @Test
    void shouldReturnConflictWhenProductAlreadyInWishlist() throws Exception {
        AddWishlistItemRequest request = new AddWishlistItemRequest(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        when(wishlistUseCase.addItem(eq(clientId), any(WishlistItem.class)))
                .thenThrow(new ProductAlreadyInWishlistException("Produto já está na wishlist"));

        String requestJson = """
                {
                  "productId": "%s",
                  "productName": "%s",
                  "productImageUrl": "%s",
                  "productPrice": %s
                }
                """.formatted(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        mockMvc.perform(post("/wishlists/{clientId}/items", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }

    // ------------------------------------------------------------
    // POST /wishlists/{clientId}/items - erro inesperado (500)
    // ------------------------------------------------------------
    @Test
    void shouldReturnInternalServerErrorOnUnexpectedErrorWhenAddingItem() throws Exception {
        AddWishlistItemRequest request = new AddWishlistItemRequest(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        when(wishlistUseCase.addItem(eq(clientId), any(WishlistItem.class)))
                .thenThrow(new RuntimeException("Erro inesperado"));

        String requestJson = """
                {
                  "productId": "%s",
                  "productName": "%s",
                  "productImageUrl": "%s",
                  "productPrice": %s
                }
                """.formatted(
                productId,
                "Tenis Nike",
                "url.jpg",
                500.0
        );

        mockMvc.perform(post("/wishlists/{clientId}/items", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId} - cenário feliz
    // ------------------------------------------------------------
    @Test
    void shouldReturnWishlistSuccessfully() throws Exception {
        WishlistItem item = new WishlistItem(
                productId,
                "Tenis Adidas",
                "https://assets.adidas.com/tenis.jpg",
                200.0
        );
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(item);

        when(wishlistUseCase.getByClientId(clientId))
                .thenReturn(Optional.of(wishlist));

        mockMvc.perform(get("/wishlists/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clientId").value(clientId.toString()))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productId").value(productId.toString()))
                .andExpect(jsonPath("$.items[0].productName").value("Tenis Adidas"))
                .andExpect(jsonPath("$.items[0].productImageUrl").value("https://assets.adidas.com/tenis.jpg"))
                .andExpect(jsonPath("$.items[0].productPrice").value(200.0));

    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId} - não encontrada (404)
    // ------------------------------------------------------------
    @Test
    void shouldReturnNotFoundWhenWishlistDoesNotExist() throws Exception {
        when(wishlistUseCase.getByClientId(clientId))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/wishlists/{clientId}", clientId))
                .andExpect(status().isNotFound());
    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId} - erro inesperado (500)
    // ------------------------------------------------------------
    @Test
    void shouldReturnInternalServerErrorOnUnexpectedErrorWhenGettingWishlist() throws Exception {
        when(wishlistUseCase.getByClientId(clientId))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/wishlists/{clientId}", clientId))
                .andExpect(status().isInternalServerError());
    }

    // ------------------------------------------------------------
    // DELETE /wishlists/{clientId}/items/{productId} - cenário feliz
    // ------------------------------------------------------------
    @Test
    void shouldRemoveItemSuccessfully() throws Exception {
        mockMvc.perform(delete("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isNoContent());
    }

    // ------------------------------------------------------------
    // DELETE /wishlists/{clientId}/items/{productId} - wishlist não encontrada (404)
    // ------------------------------------------------------------
    @Test
    void shouldReturnNotFoundWhenRemovingFromNonExistingWishlist() throws Exception {
        willThrow(new WishlistNotFoundException("Wishlist não encontrada"))
                .given(wishlistUseCase).removeItem(clientId, productId);

        mockMvc.perform(delete("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isNotFound());
    }

    // ------------------------------------------------------------
    // DELETE /wishlists/{clientId}/items/{productId} - erro inesperado (500)
    // ------------------------------------------------------------
    @Test
    void shouldReturnInternalServerErrorOnUnexpectedErrorWhenRemovingItem() throws Exception {
        willThrow(new RuntimeException("Erro inesperado"))
                .given(wishlistUseCase).removeItem(clientId, productId);

        mockMvc.perform(delete("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isInternalServerError());
    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId}/items/{productId} - produto existe (200, true)
    // ------------------------------------------------------------
    @Test
    void shouldReturnTrueWhenProductExists() throws Exception {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenReturn(true);

        mockMvc.perform(get("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId}/items/{productId} - produto não existe (200, false)
    // ------------------------------------------------------------
    @Test
    void shouldReturnFalseWhenProductDoesNotExist() throws Exception {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenReturn(false);

        mockMvc.perform(get("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    // ------------------------------------------------------------
    // GET /wishlists/{clientId}/items/{productId} - erro inesperado (500)
    // ------------------------------------------------------------
    @Test
    void shouldReturnInternalServerErrorOnUnexpectedErrorWhenCheckingProduct() throws Exception {
        when(wishlistUseCase.isProductInWishlist(clientId, productId))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/wishlists/{clientId}/items/{productId}", clientId, productId))
                .andExpect(status().isInternalServerError());
    }
}