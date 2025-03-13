package com.example.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "mohamed amgad",
                        email="mohamedamgadofficial@gmail.com"
                ),
                description = "OpenApi Documentation For Ecommerce App With SpringBoot",
                title="Ecommerce Application SpringBoot",
                version = "1.0"
        ),
        servers ={
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name ="bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class OpenApiConfig {
}
