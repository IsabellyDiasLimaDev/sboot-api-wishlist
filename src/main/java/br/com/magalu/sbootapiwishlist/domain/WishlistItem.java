package br.com.magalu.sbootapiwishlist.domain;

import java.time.Instant;

public class WishlistItem {

    private String productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private String productPrice;
    private String productCategory;
    private Instant addedAt;

    public WishlistItem(String productId, String productName, String productDescription,
                        String productImageUrl, String productPrice, String productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.addedAt = Instant.now();
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Instant getAddedAt() {
        return addedAt;
    }
}
