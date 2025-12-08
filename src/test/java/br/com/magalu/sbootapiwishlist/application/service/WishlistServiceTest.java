package br.com.magalu.sbootapiwishlist.application.service;

import br.com.magalu.sbootapiwishlist.application.port.outbound.WishlistRepositoryPort;
import br.com.magalu.sbootapiwishlist.domain.exception.ProductAlreadyInWishlistException;
import br.com.magalu.sbootapiwishlist.domain.exception.WishlistNotFoundException;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    private WishlistRepositoryPort wishlistRepositoryPort;
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        wishlistRepositoryPort = mock(WishlistRepositoryPort.class);
        wishlistService = new WishlistService(wishlistRepositoryPort);
    }

    @Test
    @DisplayName("Deve adicionar um item quando wishlist ainda nao existe para o cliente")
    void shouldAddItemWhenWishlistDoesNotExist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        WishlistItem newItem = new WishlistItem(productId, "Produto", "img", 10.0);

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.empty());

        WishlistItem result = wishlistService.addItem(clientId, newItem);

        assertEquals(newItem, result);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepositoryPort).save(wishlistCaptor.capture());
        Wishlist saved = wishlistCaptor.getValue();
        assertEquals(clientId, saved.getClientId());
        assertTrue(saved.getItems().stream().anyMatch(i -> i.getProductId().equals(productId)));
    }

    @Test
    @DisplayName("nao deve adicionar produto duplicado na wishlist")
    void shouldNotAddDuplicateProduct() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        WishlistItem existingItem = new WishlistItem(productId, "Produto", "img", 10.0);
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(existingItem);

        WishlistItem newItem = new WishlistItem(productId, "Produto 2", "img2", 20.0);

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        ProductAlreadyInWishlistException ex = assertThrows(ProductAlreadyInWishlistException.class,
                () -> wishlistService.addItem(clientId, newItem));

        assertEquals("Produto já existe na wishlist", ex.getMessage());
        verify(wishlistRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover item da wishlist existente")
    void shouldRemoveItemFromExistingWishlist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        WishlistItem item = new WishlistItem(productId, "Produto", "img", 10.0);
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(item);

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        wishlistService.removeItem(clientId, productId);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepositoryPort).save(wishlistCaptor.capture());
        Wishlist saved = wishlistCaptor.getValue();
        assertFalse(saved.productExists(productId));
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover item de wishlist inexistente")
    void shouldThrowWhenRemovingFromNonExistingWishlist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.empty());

        WishlistNotFoundException ex = assertThrows(WishlistNotFoundException.class,
                () -> wishlistService.removeItem(clientId, productId));

        assertEquals("Wishlist não encontrada", ex.getMessage());
        verify(wishlistRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar true quando produto estiver na wishlist")
    void shouldReturnTrueWhenProductIsInWishlist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(new WishlistItem(productId, "Produto", "img", 10.0));

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertTrue(wishlistService.isProductInWishlist(clientId, productId));
    }

    @Test
    @DisplayName("Deve retornar false quando produto nao estiver na wishlist")
    void shouldReturnFalseWhenProductIsNotInWishlist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertFalse(wishlistService.isProductInWishlist(clientId, productId));
    }

    @Test
    @DisplayName("Deve retornar false quando wishlist do cliente nao existe")
    void shouldReturnFalseWhenWishlistDoesNotExist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.empty());

        assertFalse(wishlistService.isProductInWishlist(clientId, productId));
    }

    @Test
    @DisplayName("Deve retornar wishlist por clientId")
    void shouldGetWishlistByClientId() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        Optional<Wishlist> result = wishlistService.getByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(clientId, result.get().getClientId());
    }

    @Test
    @DisplayName("Deve retornar vazio quando wishlist com clientId nao existe")
    void shouldReturnEmptyWhenWishlistByClientIdDoesNotExist() {
        UUID clientId = UUID.randomUUID();

        when(wishlistRepositoryPort.findByClientId(clientId)).thenReturn(Optional.empty());

        Optional<Wishlist> result = wishlistService.getByClientId(clientId);

        assertTrue(result.isEmpty());
    }
}