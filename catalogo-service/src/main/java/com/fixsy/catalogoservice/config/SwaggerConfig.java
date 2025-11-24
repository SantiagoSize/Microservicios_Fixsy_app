package com.fixsy.catalogoservice.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Fixsy - Services API",
                version = "v1",
                description = "API para catálogo de servicios de Fixsy",
                contact = @Contact(name = "Fixsy Team", email = "support@fixsy.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
                termsOfService = "https://fixsy.com/terms"
        ),
        servers = {
                @Server(url = "http://localhost:8083", description = "Local"),
                @Server(url = "https://api.dev.fixsy.com", description = "Dev"),
                @Server(url = "https://api.fixsy.com", description = "Prod")
        },
        tags = {
                @Tag(name = "Services", description = "Operaciones sobre catálogo de servicios")
        },
        externalDocs = @ExternalDocumentation(
                description = "Wiki de microservicios Fixsy",
                url = "https://docs.fixsy.com/microservices"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Autenticación JWT emitida por el gateway"
)
@Configuration
public class SwaggerConfig {
    @Bean
    io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Fixsy - Services API")
                        .version("v1")
                        .description("API para catálogo de servicios de Fixsy")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Team")
                                .email("support@fixsy.com")))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .description("Wiki de microservicios Fixsy")
                        .url("https://docs.fixsy.com/microservices"))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8083").description("Local"),
                        new io.swagger.v3.oas.models.servers.Server().url("https://api.dev.fixsy.com").description("Dev"),
                        new io.swagger.v3.oas.models.servers.Server().url("https://api.fixsy.com").description("Prod")
                ));
    }
}
