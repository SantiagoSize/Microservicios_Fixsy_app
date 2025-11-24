# Documentación de Swagger - Microservicios Fixsy

Guía para acceder y utilizar la documentación interactiva de las APIs de Fixsy.

## URLs de Acceso

### Usuarios Service (Puerto 8081)
Swagger UI: http://localhost:8081/swagger-ui/index.html  
API Docs: http://localhost:8081/v3/api-docs  
Descripción: Gestión de usuarios, autenticación y perfiles de mecánicos.

### Solicitudes Service (Puerto 8082)
Swagger UI: http://localhost:8082/swagger-ui/index.html  
API Docs: http://localhost:8082/v3/api-docs  
Descripción: Gestión de solicitudes de servicio, asignación de mecánicos y seguimiento de estados.

### Catálogo Service (Puerto 8083)
Swagger UI: http://localhost:8083/swagger-ui/index.html  
API Docs: http://localhost:8083/v3/api-docs  
Descripción: Catálogo de servicios de mantenimiento y reparación con precios y tiempos estimados.

### Reviews Service (Puerto 8084)
Swagger UI: http://localhost:8084/swagger-ui/index.html  
API Docs: http://localhost:8084/v3/api-docs  
Descripción: Sistema de reseñas y calificaciones de mecánicos (rating 1-5 estrellas).

### Vehículos Service (Puerto 8085)
Swagger UI: http://localhost:8085/swagger-ui/index.html  
API Docs: http://localhost:8085/v3/api-docs  
Descripción: Gestión de vehículos de usuarios con validación de placas únicas.

## Descripción de Servicios

### Usuarios Service
- Registro y autenticación de usuarios (Clientes, Mecánicos, Administradores)
- Gestión de perfiles de mecánicos con especialidades y disponibilidad
- Gestión de direcciones de usuarios
- Roles: CLIENT, MECHANIC, ADMIN

### Solicitudes Service
- Creación de solicitudes de servicio por clientes
- Asignación de mecánicos a solicitudes
- Seguimiento de estados: Pendiente, En Proceso, Completada, Cancelada
- Consulta de solicitudes por cliente o mecánico

### Catálogo Service
- Catálogo de servicios de mantenimiento y reparación
- Precios base y tiempos estimados por servicio
- Gestión completa del catálogo (CRUD)
- Tipos: Mantenimiento preventivo, Reparaciones, Diagnósticos, Servicios especializados

### Reviews Service
- Calificaciones de 1 a 5 estrellas
- Comentarios sobre la experiencia del servicio
- Consulta de reseñas por mecánico
- Historial de calificaciones

### Vehículos Service
- Registro de vehículos por usuario
- Gestión de vehículo predeterminado
- Validación de placa única por usuario
- Información completa: marca, modelo, año, color, placa

## Cómo Usar Swagger

### Acceso Básico
1. Asegúrate de que los microservicios estén corriendo
2. Abre tu navegador y accede a cualquiera de las URLs de Swagger UI
3. Explora los endpoints disponibles en la interfaz interactiva

### Probar Endpoints
1. Expande un endpoint haciendo clic en él
2. Haz clic en "Try it out"
3. Completa los parámetros requeridos
4. Haz clic en "Execute"
5. Revisa la respuesta del servidor

### Autenticación
Los servicios están configurados con esquema de autenticación JWT (Bearer Token), aunque actualmente no está implementado en los endpoints. Para producción:
- Obtener un token JWT del servicio de autenticación
- Hacer clic en el botón "Authorize" en Swagger UI
- Ingresar el token: Bearer <tu-token>

## Endpoints de API Docs

Acceso directo al JSON de la documentación OpenAPI:

| Servicio | Endpoint |
|----------|----------|
| Usuarios | http://localhost:8081/v3/api-docs |
| Solicitudes | http://localhost:8082/v3/api-docs |
| Catálogo | http://localhost:8083/v3/api-docs |
| Reviews | http://localhost:8084/v3/api-docs |
| Vehículos | http://localhost:8085/v3/api-docs |

Estos endpoints devuelven el JSON completo de la especificación OpenAPI 3.0, útil para:
- Integración con herramientas externas
- Generación de clientes SDK
- Importación en Postman o Insomnia

## Requisitos Previos

- MySQL debe estar corriendo en localhost:3306
- Java 21 instalado
- Maven 3.6+ instalado
- Todos los microservicios deben estar iniciados

## Iniciar Servicios

Para iniciar todos los servicios, ejecuta desde la raíz del proyecto:
```powershell
.\start-fixsy-services.ps1
```

## Solución de Problemas

Error 500 en Swagger:
- Verifica que los servicios estén completamente iniciados
- Revisa los logs en las ventanas de PowerShell
- Asegúrate de que MySQL esté corriendo

Puerto en uso:
- Cierra las ventanas de PowerShell donde están corriendo los servicios anteriores

No se puede acceder a Swagger:
- Verifica que el servicio esté corriendo en el puerto correcto
- Revisa el firewall de Windows
- Intenta acceder desde http://127.0.0.1 en lugar de localhost

Acceso desde Dispositivos en la Red Local:
Si quieres acceder desde otro dispositivo en tu red local, reemplaza localhost con la IP de tu PC:
```
http://192.168.1.X:8081/swagger-ui/index.html
```

## Versión de la Documentación

- Versión de API: v1.0
- Spring Boot: 3.5.8
- SpringDoc OpenAPI: 2.7.0
- Última actualización: Noviembre 2025

## Configuración Técnica

Dependencias:
- springdoc-openapi-starter-webmvc-ui:2.7.0
- Compatible con Spring Boot 3.5.8

Configuración:
Cada servicio tiene su propia configuración de Swagger en:
{service-name}/src/main/java/com/fixsy/{service}service/config/SwaggerConfig.java

Propiedades en application.properties:
```properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.enabled=true
```

## Soporte

Para más información o reportar problemas:
- Email: dev@fixsy.com
- Documentación: https://fixsy.com/support

Fixsy Development Team

