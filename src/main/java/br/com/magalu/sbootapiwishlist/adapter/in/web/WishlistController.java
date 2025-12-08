package br.com.magalu.sbootapiwishlist.adapter.in.web;

import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.AddWishlistItemRequest;
import br.com.magalu.sbootapiwishlist.adapter.in.web.dto.WishlistResponse;
import br.com.magalu.sbootapiwishlist.adapter.in.web.mapper.WishlistItemMapper;
import br.com.magalu.sbootapiwishlist.adapter.in.web.mapper.WishlistMapper;
import br.com.magalu.sbootapiwishlist.application.port.inbound.WishlistUseCase;
import br.com.magalu.sbootapiwishlist.domain.model.WishlistItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Wishlist", description = "Operações de gerenciamento da wishlist")
@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistUseCase wishlistUseCase;

    public WishlistController(WishlistUseCase wishlistUseCase) {
        this.wishlistUseCase = wishlistUseCase;
    }

    @Operation(
            summary = "Adiciona item na wishlist",
            description = "Adiciona um novo produto na wishlist do cliente informado"
    )
    @PostMapping("/{clientId}/items")
    public ResponseEntity<Void> addItem(
            @Parameter(
                    description = "Identificador do cliente",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID clientId,
            @RequestBody AddWishlistItemRequest request
    ) {
        WishlistItem item = WishlistItemMapper.toDomain(request);
        WishlistItem createdItem = wishlistUseCase.addItem(clientId, item);

        URI location = URI.create(String.format(
                "/wishlists/%s/items/%s",
                clientId,
                createdItem.getProductId()
        ));

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Obtém a wishlist do cliente",
            description = "Retorna a lista completa de itens da wishlist"
    )
    @GetMapping("/{clientId}")
    public ResponseEntity<WishlistResponse> getWishlist(
            @Parameter(
                    description = "Identificador do cliente",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID clientId
    ) {
        return wishlistUseCase.getByClientId(clientId)
                .map(WishlistMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Remove item da wishlist",
            description = "Remove o produto da wishlist do cliente"
    )
    @DeleteMapping("/{clientId}/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @Parameter(
                    description = "Identificador do cliente",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID clientId,
            @Parameter(
                    description = "Identificador do produto",
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID productId
    ) {
        wishlistUseCase.removeItem(clientId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Verifica se o produto está na wishlist",
            description = "Retorna true/false indicando se o produto está presente"
    )
    @GetMapping("/{clientId}/items/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(
            @Parameter(
                    description = "Identificador do cliente",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID clientId,
            @Parameter(
                    description = "Identificador do produto",
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID productId
    ) {
        boolean exists = wishlistUseCase.isProductInWishlist(clientId, productId);

        return ResponseEntity.ok(exists);
    }
}