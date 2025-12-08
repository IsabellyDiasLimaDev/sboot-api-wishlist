package br.com.magalu.sbootapiwishlist.adapter.out.persistence.document;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WishlistItemDocument {

    private UUID productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private Double productPrice;
    private String productCategory;

    public WishlistItemDocument(UUID productId, String productName, String productDescription, String productImageUrl, Double productPrice, String productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }
}
