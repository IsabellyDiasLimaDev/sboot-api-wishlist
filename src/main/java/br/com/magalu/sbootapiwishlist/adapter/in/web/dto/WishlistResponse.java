package br.com.magalu.sbootapiwishlist.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(name = "WishlistResponse", description = "Resposta da wishlist do cliente")
public class WishlistResponse {

    @Schema(description = "Identificador do cliente", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID clientId;
    @Schema(description = "Lista de itens na wishlist do cliente")
    private List<ItemResponse> items;

    @Data
    public static class ItemResponse {
        @Schema(description = "Identificador do produto", example = "26962d82-a6a6-4b43-8ad8-b2c8b1f65dc4")
        private UUID productId;
        @Schema(description = "Nome do produto", example = "Tenis Adidas")
        private String productName;
        @Schema(description = "URL da imagem do produto", example = "https://assets.adidas.com/tenis.jpg")
        private String productImageUrl;
        @Schema(description = "Preço do produto em reais", example = "200.00")
        private Double productPrice;
    }
}
