package br.com.magalu.sbootapiwishlist.adapter.in.web;

import br.com.magalu.sbootapiwishlist.domain.exception.ProductAlreadyInWishlistException;
import br.com.magalu.sbootapiwishlist.domain.exception.WishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<Void> handleWishlistNotFound(WishlistNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<Void> handleProductAlreadyInWishlist(ProductAlreadyInWishlistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleUnexpected(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

