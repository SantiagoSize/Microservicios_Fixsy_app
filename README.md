# Fixsy - Microservicios (Spring Boot)

Este repositorio contiene los 5 microservicios de Fixsy desarrollados con Java 21, Spring Boot 3.5.8 y Maven. Cada microservicio incluye Swagger, DTOs, validaciones y manejo de errores consistente.

## Microservicios

### usuarios-service (Puerto 8081)
Gestión de usuarios, autenticación y perfiles de mecánicos.
- Swagger UI: http://localhost:8081/swagger-ui/index.html
- API Docs: http://localhost:8081/v3/api-docs
- Funcionalidades: Registro y autenticación de usuarios, gestión de perfiles de mecánicos, gestión de direcciones
- Roles: CLIENT, MECHANIC, ADMIN

### solicitudes-service (Puerto 8082)
Gestión de solicitudes de servicio, asignación de mecánicos y seguimiento de estados.
- Swagger UI: http://localhost:8082/swagger-ui/index.html
- API Docs: http://localhost:8082/v3/api-docs
- Funcionalidades: Creación de solicitudes, asignación de mecánicos, seguimiento de estados
- Estados: Pendiente, En Proceso, Completada, Cancelada

### catalogo-service (Puerto 8083)
Catálogo de servicios de mantenimiento y reparación con precios y tiempos estimados.
- Swagger UI: http://localhost:8083/swagger-ui/index.html
- API Docs: http://localhost:8083/v3/api-docs
- Funcionalidades: Gestión completa del catálogo de servicios (CRUD)
- Tipos: Mantenimiento preventivo, Reparaciones, Diagnósticos, Servicios especializados

### reviews-service (Puerto 8084)
Sistema de reseñas y calificaciones de mecánicos.
- Swagger UI: http://localhost:8084/swagger-ui/index.html
- API Docs: http://localhost:8084/v3/api-docs
- Funcionalidades: Calificaciones de 1 a 5 estrellas, comentarios, consulta de reseñas por mecánico
- Rating: 1 (Muy malo) a 5 (Excelente)

### vehiculos-service (Puerto 8085)
Gestión de vehículos de usuarios con validación de placas únicas.
- Swagger UI: http://localhost:8085/swagger-ui/index.html
- API Docs: http://localhost:8085/v3/api-docs
- Funcionalidades: Registro de vehículos, gestión de vehículo predeterminado, validación de placa única
- Características: Múltiples vehículos por usuario, un vehículo predeterminado por usuario

## Requisitos

- Java 21
- Maven 3.6+
- MySQL 8.0+
- Spring Boot 3.5.8
- SpringDoc OpenAPI 2.7.0

## Configuración de Bases de Datos

Cada servicio define su propia base de datos en `application.properties`:

- usuarios-service: `usuarios_service`
- solicitudes-service: `service_requests_db`
- catalogo-service: `services_db`
- reviews-service: `reviews_db`
- vehiculos-service: `vehiculos_service`

Las bases de datos se crean automáticamente si no existen. Configura las credenciales en `application.properties` de cada servicio:

```properties
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS:}
```

## Cómo Ejecutar

### Iniciar Todos los Servicios

Desde la raíz del proyecto, ejecuta:

```powershell
.\start-fixsy-services.ps1
```

Este script abre 5 ventanas de PowerShell, una para cada microservicio, y los inicia automáticamente.

### Iniciar Servicios Individualmente

Para iniciar un servicio específico:

```powershell
cd usuarios-service
mvn spring-boot:run
```

Repite el proceso para cada servicio:
- usuarios-service
- solicitudes-service
- catalogo-service
- reviews-service
- vehiculos-service

## Documentación de Swagger

Para información detallada sobre cómo usar Swagger, consulta el archivo:
- `SWAGGER_DOCUMENTATION.md`

Este archivo contiene:
- URLs de acceso a Swagger UI de todos los servicios
- Guía de uso de Swagger
- Endpoints de API Docs
- Solución de problemas comunes

## Estructura de Proyectos

Cada microservicio sigue la misma estructura:

```
{service-name}/
├── src/
│   ├── main/
│   │   ├── java/com/fixsy/{service}service/
│   │   │   ├── controller/     # Controladores REST
│   │   │   ├── service/        # Lógica de negocio
│   │   │   ├── repository/     # Repositorios JPA
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── config/          # Configuraciones (Swagger, etc.)
│   │   │   └── exception/      # Manejo de excepciones
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql      # Script de creación de tablas
│   │       └── data.sql        # Datos iniciales
│   └── test/                   # Tests unitarios
└── pom.xml
```

## Características Comunes

### Manejo de Errores
- `ErrorResponse`: DTO estándar para respuestas de error
- `GlobalExceptionHandler`: Manejo global de excepciones con `@RestControllerAdvice`
- Uso de `ResponseStatusException` para errores HTTP

### Validaciones
- Bean Validation en todos los DTOs
- Anotaciones: `@NotBlank`, `@Size`, `@Min`, `@Max`, `@Email`, `@NotNull`
- Validación automática en endpoints REST

### Documentación API
- Swagger UI integrado en cada servicio
- Documentación completa de endpoints con ejemplos
- Especificación OpenAPI 3.0 disponible en `/v3/api-docs`

### Configuración JPA
- `spring.jpa.hibernate.ddl-auto=none`: No genera esquemas automáticamente
- Uso de `schema.sql` para creación de tablas
- Uso de `data.sql` para datos iniciales
- `spring.sql.init.mode=always`: Siempre ejecuta scripts SQL

## Notas Importantes

- Las contraseñas se almacenan en texto plano (pendiente implementar hashing para producción)
- Cada servicio tiene su propia base de datos MySQL
- Los servicios se comunican de forma independiente (sin API Gateway por ahora)
- La autenticación JWT está configurada en Swagger pero no implementada en los endpoints

## Solución de Problemas

### Error 500 en Swagger
- Verifica que los servicios estén completamente iniciados
- Revisa los logs en las ventanas de PowerShell
- Asegúrate de que MySQL esté corriendo

### Puerto en uso
- Cierra las ventanas de PowerShell donde están corriendo los servicios anteriores
- Verifica qué proceso está usando el puerto con: `Get-NetTCPConnection -LocalPort 8081`

### No se puede acceder a Swagger
- Verifica que el servicio esté corriendo en el puerto correcto
- Revisa el firewall de Windows
- Intenta acceder desde `http://127.0.0.1` en lugar de `localhost`

### Error de conexión a MySQL
- Verifica que MySQL esté corriendo
- Revisa las credenciales en `application.properties`
- Asegúrate de que el puerto 3306 esté disponible

## Versiones

- Java: 21
- Spring Boot: 3.5.8
- SpringDoc OpenAPI: 2.7.0
- MySQL Connector: 9.5.0
- Maven: 3.6+

## Contacto

Para más información o soporte:
- Email: dev@fixsy.com
- Documentación: https://fixsy.com/support

Fixsy Development Team
