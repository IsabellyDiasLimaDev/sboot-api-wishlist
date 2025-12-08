package br.com.magalu.sbootapiwishlist.domain.exception;

public class WishlistNotFoundException extends RuntimeException {

    public WishlistNotFoundException(String message) {
        super(message);
    }
}

