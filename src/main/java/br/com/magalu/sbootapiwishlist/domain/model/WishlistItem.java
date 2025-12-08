package br.com.magalu.sbootapiwishlist.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class WishlistItem {

    private final UUID productId;
    private final String productName;
    private final String productDescription;
    private final String productImageUrl;
    private final Double productPrice;
    private final String productCategory;
    private final Instant addedAt;

    public WishlistItem(UUID productId, String productName, String productDescription,
                        String productImageUrl, Double productPrice, String productCategory) {
        this.productId = Objects.requireNonNull(productId, "productId can not be null");
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.addedAt = Instant.now();
    }
}
