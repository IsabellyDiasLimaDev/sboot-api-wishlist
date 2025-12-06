package br.com.magalu.sbootapiwishlist.domain;

import java.time.Instant;
import java.util.Objects;

public class WishlistItem {

    private final String productId;
    private final String productName;
    private final String productDescription;
    private final String productImageUrl;
    private final String productPrice;
    private final String productCategory;
    private final Instant addedAt;

    public WishlistItem(String productId, String productName, String productDescription,
                        String productImageUrl, String productPrice, String productCategory) {
        this.productId = Objects.requireNonNull(productId, "productId can not be null");
        if (this.productId.isBlank()) {
            throw new IllegalArgumentException("productId can not be blank");
        }
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
}
