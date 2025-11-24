package com.fixsy.catalogoservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Fixsy - Services Catalog API")
                        .version("v1.0")
                        .description("API para gestión del catálogo de servicios de Fixsy. " +
                                "Funcionalidades: Catálogo de servicios de mantenimiento y reparación, " +
                                "precios base y tiempos estimados por servicio, " +
                                "gestión completa del catálogo (CRUD). " +
                                "Tipos de servicios: Mantenimiento preventivo, Reparaciones, " +
                                "Diagnósticos y revisiones, Servicios especializados.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Development Team")
                                .email("dev@fixsy.com")
                                .url("https://fixsy.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Proprietary")
                                .url("https://fixsy.com/terms")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8083")
                                .description("Servidor Local de Desarrollo")
                ));
    }
}
