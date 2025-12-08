package br.com.magalu.sbootapiwishlist.adapter.in.web;

import br.com.magalu.sbootapiwishlist.domain.exception.ProductAlreadyInWishlistException;
import br.com.magalu.sbootapiwishlist.domain.exception.WishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        // Erros de validação de entrada / regras de negócio inválidas
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        // Estados inválidos de regra de negócio (ex: limite de itens)
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWishlistNotFound(WishlistNotFoundException ex) {
        // Wishlist do cliente não encontrada
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<Map<String, Object>> handleProductAlreadyInWishlist(ProductAlreadyInWishlistException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
        return ResponseEntity.status(status).body(body);
    }
}
