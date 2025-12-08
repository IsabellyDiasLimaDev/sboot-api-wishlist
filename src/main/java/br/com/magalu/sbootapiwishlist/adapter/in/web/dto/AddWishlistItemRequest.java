package br.com.magalu.sbootapiwishlist.adapter.in.web.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Data
@Schema(name = "AddWishlistItemRequest", description = "Requisição para adicionar um item na wishlist")
public class AddWishlistItemRequest {

    @Schema(description = "Identificador do produto", example = "26962d82-a6a6-4b43-8ad8-b2c8b1f65dc4")
    private UUID productId;

    @Schema(description = "Nome do produto", example = "Tenis Adidas")
    private String productName;

    @Schema(description = "URL da imagem do produto", example = "https://assets.adidas.com/tenis.jpg")
    private String productImageUrl;

    @Schema(description = "Preço do produto em reais", example = "200.00")
    private Double productPrice;


    public AddWishlistItemRequest(UUID productId, String productName, String productImageUrl, Double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }
}
