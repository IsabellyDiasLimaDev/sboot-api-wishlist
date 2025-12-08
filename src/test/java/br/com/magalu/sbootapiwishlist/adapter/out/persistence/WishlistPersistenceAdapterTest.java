package br.com.magalu.sbootapiwishlist.adapter.out.persistence;

import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistDocument;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.mapper.WishlistDocumentMapper;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.repository.WishlistMongoRepository;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistPersistenceAdapterTest {

    private WishlistMongoRepository repository;
    private WishlistDocumentMapper mapper;
    private WishlistPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(WishlistMongoRepository.class);
        mapper = mock(WishlistDocumentMapper.class);
        adapter = new WishlistPersistenceAdapter(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar wishlist quando encontrada por clientId")
    void shouldReturnWishlistWhenFoundByClientId() {
        UUID clientId = UUID.randomUUID();
        WishlistDocument document = new WishlistDocument(
                clientId,
                new ArrayList<>()
        );
        Wishlist wishlist = new Wishlist(clientId);

        when(repository.findById(clientId)).thenReturn(Optional.of(document));
        when(mapper.toDomain(document)).thenReturn(wishlist);

        Optional<Wishlist> result = adapter.findByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(clientId, result.get().getClientId());
        verify(repository).findById(clientId);
        verify(mapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve retornar Optional.empty quando wishlist nao encontrada por clientId")
    void shouldReturnEmptyWhenWishlistNotFoundByClientId() {
        UUID clientId = UUID.randomUUID();

        when(repository.findById(clientId)).thenReturn(Optional.empty());

        Optional<Wishlist> result = adapter.findByClientId(clientId);

        assertTrue(result.isEmpty());
        verify(repository).findById(clientId);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve salvar wishlist convertendo para document")
    void shouldSaveWishlistConvertingToDocument() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = new Wishlist(clientId);
        WishlistDocument document = new WishlistDocument(
                clientId,
                new ArrayList<>()
        );

        when(mapper.toDocument(wishlist)).thenReturn(document);

        adapter.save(wishlist);

        verify(mapper).toDocument(wishlist);
        verify(repository).save(document);
    }
}