package br.com.magalu.sbootapiwishlist.adapter.out.persistence.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Document(collection = "wishlists")
public class WishlistDocument {
    @Id
    private UUID clientId;

    private List<WishlistItemDocument> items;

    public  WishlistDocument(UUID clientId, List<WishlistItemDocument> items) {
        this.clientId = clientId;
        this.items = items;
    }
}
