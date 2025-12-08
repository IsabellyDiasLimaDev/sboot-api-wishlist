package br.com.magalu.sbootapiwishlist.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class WishlistItem {

    private final UUID productId;
    private final String productName;
    private final String productImageUrl;
    private final Double productPrice;

    public WishlistItem(UUID productId, String productName,
                        String productImageUrl, Double productPrice) {
        this.productId = Objects.requireNonNull(productId, "productId can not be null");
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }
}
