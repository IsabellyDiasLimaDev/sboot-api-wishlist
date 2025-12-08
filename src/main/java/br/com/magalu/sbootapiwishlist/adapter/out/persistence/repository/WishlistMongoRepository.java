package br.com.magalu.sbootapiwishlist.adapter.out.persistence.repository;

import br.com.magalu.sbootapiwishlist.adapter.out.persistence.document.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WishlistMongoRepository extends MongoRepository<WishlistDocument, UUID> {
}
