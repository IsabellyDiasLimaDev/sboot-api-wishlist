package br.com.magalu.sbootapiwishlist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Wishlist Magalu",
                version = "1.0.0",
                description = "API para gerenciamento de wishlist de produtos",
                contact = @Contact(
                        name = "Time Wishlist",
                        email = "wishlist@magalu.com.br"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class OpenApiConfig {
}
