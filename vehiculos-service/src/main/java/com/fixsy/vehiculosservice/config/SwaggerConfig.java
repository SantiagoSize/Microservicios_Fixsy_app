package com.fixsy.vehiculosservice.config;

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
                        .title("Fixsy - Vehicles API")
                        .version("v1.0")
                        .description("API para gestión de vehículos de usuarios en Fixsy. " +
                                "Funcionalidades: Registro de vehículos por usuario (clientes), " +
                                "gestión de vehículo predeterminado, validación de placa única por usuario, " +
                                "información completa del vehículo (marca, modelo, año, color, placa). " +
                                "Características: Un usuario puede tener múltiples vehículos, " +
                                "solo un vehículo puede ser predeterminado por usuario, " +
                                "la placa debe ser única por usuario.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Development Team")
                                .email("dev@fixsy.com")
                                .url("https://fixsy.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Proprietary")
                                .url("https://fixsy.com/terms")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8085")
                                .description("Servidor Local de Desarrollo")
                ));
    }
}
