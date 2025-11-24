package com.fixsy.usuariosservice.config;

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
                        .title("Fixsy - Usuarios Service API")
                        .version("v1.0")
                        .description("API para gestión de usuarios y perfiles de mecánicos en Fixsy. " +
                                "Funcionalidades: Registro y autenticación de usuarios (Clientes, Mecánicos, Administradores), " +
                                "gestión de perfiles de mecánicos con especialidades y disponibilidad, " +
                                "gestión de direcciones de usuarios. " +
                                "Roles disponibles: CLIENT (Cliente que solicita servicios), " +
                                "MECHANIC (Mecánico que ofrece servicios), ADMIN (Administrador del sistema).")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Development Team")
                                .email("dev@fixsy.com")
                                .url("https://fixsy.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Proprietary")
                                .url("https://fixsy.com/terms")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8081")
                                .description("Servidor Local de Desarrollo")
                ));
    }
}
