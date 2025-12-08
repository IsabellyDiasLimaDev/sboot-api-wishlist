package br.com.magalu.sbootapiwishlist.adapter.out.persistence;

import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistDocument;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.mapper.WishlistDocumentMapper;
import br.com.magalu.sbootapiwishlist.adapter.out.persistence.repository.WishlistMongoRepository;
import br.com.magalu.sbootapiwishlist.application.port.outbound.WishlistRepositoryPort;
import br.com.magalu.sbootapiwishlist.domain.model.Wishlist;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WishlistPersistenceAdapter implements WishlistRepositoryPort {

    private final WishlistMongoRepository repository;
    private final WishlistDocumentMapper mapper;

    public WishlistPersistenceAdapter(WishlistMongoRepository repository,
                                      WishlistDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Wishlist> findByClientId(UUID clientId) {
        return repository.findById(clientId).map(mapper::toDomain);
    }

    @Override
    public void save(Wishlist wishlist) {
        WishlistDocument doc = mapper.toDocument(wishlist);
        repository.save(doc);
    }

}
