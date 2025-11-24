package com.fixsy.solicitudesservice.config;

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
                        .title("Fixsy - Service Requests API")
                        .version("v1.0")
                        .description("API para gestión de solicitudes de servicio en Fixsy. " +
                                "Funcionalidades: Creación de solicitudes de servicio por clientes, " +
                                "asignación de mecánicos a solicitudes, seguimiento de estados " +
                                "(Pendiente, En Proceso, Completada, Cancelada), " +
                                "consulta de solicitudes por cliente o mecánico. " +
                                "Estados: Pendiente (esperando asignación), En Proceso (trabajo en curso), " +
                                "Completada (servicio finalizado), Cancelada (solicitud cancelada).")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Fixsy Development Team")
                                .email("dev@fixsy.com")
                                .url("https://fixsy.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Proprietary")
                                .url("https://fixsy.com/terms")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8082")
                                .description("Servidor Local de Desarrollo")
                ));
    }
}
