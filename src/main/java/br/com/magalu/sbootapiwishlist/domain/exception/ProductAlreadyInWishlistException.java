package br.com.magalu.sbootapiwishlist.domain.exception;

public class ProductAlreadyInWishlistException extends RuntimeException {

    public ProductAlreadyInWishlistException(String message) {
        super(message);
    }
}

