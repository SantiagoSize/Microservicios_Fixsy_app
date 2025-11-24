# Fixsy - Microservicios (Spring Boot)

Este repositorio contiene los 5 microservicios de Fixsy (Java 21 + Spring Boot 3.4.6 + Maven), cada uno con Swagger, DTOs, validaciones y manejo de errores consistente.

## Microservicios activos

1) **usuarios-service** (puerto 8081)  
   - Gestión de usuarios (roles CLIENT, MECHANIC, ADMIN).
   - Swagger: `http://localhost:8081/swagger-ui/index.html`
   - API Docs: `http://localhost:8081/api-docs`

2) **vehiculos-service** (puerto 8085)  
   - Gestión de vehículos por usuario, placa única y vehículo predeterminado.
   - Swagger: `http://localhost:8085/swagger-ui/index.html`
   - API Docs: `http://localhost:8085/api-docs`

3) **solicitudes-service** (puerto 8082)  
   - Solicitudes de servicio (cliente, mecánico, vehículo, estado).
   - Swagger: `http://localhost:8082/swagger-ui/index.html`
   - API Docs: `http://localhost:8082/api-docs`

4) **catalogo-service** (puerto 8083)  
   - Catálogo de servicios (mantenciones, reparaciones, precios base).
   - Swagger: `http://localhost:8083/swagger-ui/index.html`
   - API Docs: `http://localhost:8083/api-docs`

5) **reviews-service** (puerto 8084)  
   - Reseñas de mecánicos (rating 1–5, comentario).
   - Swagger: `http://localhost:8084/swagger-ui/index.html`
   - API Docs: `http://localhost:8084/api-docs`

## Requisitos
- Java 21
- Maven 3.6+
- MySQL 8.0+
- Spring Boot 3.4.6

## Configuración de bases de datos
Cada servicio define su propia URL en `application.properties`. Ejemplos:
- usuarios-service: `jdbc:mysql://localhost:3306/usuarios_service?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
- vehiculos-service: `jdbc:mysql://localhost:3306/vehiculos_service?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
- solicitudes-service: `jdbc:mysql://localhost:3306/service_requests_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
- catalogo-service: `jdbc:mysql://localhost:3306/services_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
- reviews-service: `jdbc:mysql://localhost:3306/reviews_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`

Ajusta `spring.datasource.username` y `spring.datasource.password` según tu entorno.

## Cómo ejecutar

### Opción 1: script multi-ventana
Desde la raíz del repo:
```powershell
.\start-fixsy-services.ps1
```
Abre 5 ventanas de PowerShell y levanta cada microservicio con mvnw/mvn.

### Opción 2: individual
```powershell
cd usuarios-service
.\mvnw spring-boot:run
# o mvn spring-boot:run si no hay wrapper
```
Repite para cada carpeta (`vehiculos-service`, `solicitudes-service`, `catalogo-service`, `reviews-service`).

## Dashboard

- `fixsy-dashboard.ps1`: genera `status.json` y `dashboard.html` (auto-refresh) consultando `/api-docs` de cada servicio y abre el dashboard en el navegador.
- `SWAGGER_URLS.md`: contiene los enlaces rápidos a Swagger UI y API Docs de los 5 servicios.

## Estructura común
- Paquetes: `controller`, `service`, `repository`, `entity`, `dto`, `config`, `exception`.
- Errores: `ErrorResponse` + `GlobalExceptionHandler` con `ResponseStatusException`.
- Validaciones: Bean Validation en DTOs (ej. @NotBlank, @Size, @Min, @Max).
- Swagger: springdoc-openapi (`/swagger-ui`, `/api-docs`).

## Notas
- `spring.jpa.hibernate.ddl-auto=none` en todos los servicios; cada uno usa su `schema.sql`.
- Contraseñas en texto plano en DTO/entidades (pendiente hashing en producción).
- Usa `start-fixsy-services.ps1` para levantar todo y `fixsy-dashboard.ps1` para monitorear estados. 
