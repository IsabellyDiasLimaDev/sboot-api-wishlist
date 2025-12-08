package br.com.magalu.sbootapiwishlist.adapter.in.web.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Data
@Schema(name = "AddWishlistItemRequest", description = "Requisição para adicionar um item na wishlist")
public class AddWishlistItemRequest {

    @Schema(description = "Identificador do produto", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID productId;

    @Schema(description = "Nome do produto", example = "Tenis Adidas")
    private String productName;

    @Schema(description = "Descrição do produto", example = "Tenis Adidas Running")
    private String productDescription;

    @Schema(description = "URL da imagem do produto", example = "https://assets.adidas.com/tenis.jpg")
    private String productImageUrl;

    @Schema(description = "Preço do produto em reais", example = "200.00")
    private Double productPrice;

    @Schema(description = "Categoria do produto", example = "Tenis")
    private String productCategory;

    public AddWishlistItemRequest(UUID productId, String productName, String productDescription, String productImageUrl, Double productPrice, String productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }
}
