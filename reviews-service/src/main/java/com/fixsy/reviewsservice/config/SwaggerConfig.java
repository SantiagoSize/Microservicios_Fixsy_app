package com.fixsy.reviewsservice.config;

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
                        .title("Fixsy - Reviews API")
                        .version("v1.0")
                        .description("API para gestión de reseñas y calificaciones de mecánicos en Fixsy. " +
                                "Funcionalidades: Los clientes pueden calificar y comentar sobre el servicio recibido, " +
                                "sistema de rating de 1 a 5 estrellas, consulta de reseñas por mecánico, " +
                                "historial de calificaciones. Sistema de calificación: Rating de 1 (Muy malo) " +
                                "a 5 (Excelente), comentarios opcionales sobre la experiencia, " +
                                "una reseña por solicitud completada.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Development Team")
                                .email("dev@fixsy.com")
                                .url("https://fixsy.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Proprietary")
                                .url("https://fixsy.com/terms")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8084")
                                .description("Servidor Local de Desarrollo")
                ));
    }
}
