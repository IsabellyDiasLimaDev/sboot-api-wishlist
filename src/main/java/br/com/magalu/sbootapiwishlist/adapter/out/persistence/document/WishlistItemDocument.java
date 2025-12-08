package br.com.magalu.sbootapiwishlist.adapter.out.persistence.document;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WishlistItemDocument {

    private UUID productId;
    private String productName;
    private String productImageUrl;
    private Double productPrice;

    public WishlistItemDocument(UUID productId, String productName, String productImageUrl, Double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }
}
