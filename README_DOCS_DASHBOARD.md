# Dashboard y Documentación de Microservicios

## Requisitos previos
- PowerShell instalado.
- Microservicios corriendo en los puertos indicados:  
  usuarios (8081), gestionsolicitudes (8082), vehiculos (8085).
- Swagger: interfaz web de documentación de APIs disponible en `http://localhost:<puerto>/swagger-ui/index.html`.

## Scripts disponibles

### generate-microservices-dashboard.ps1
- Qué hace: genera un dashboard HTML (`microservices-dashboard.html`) con el estado Online/Offline de cada microservicio y abre el archivo en el navegador. Se auto-refresca cada 10 segundos.
- Cómo ejecutarlo:  
  1. Abrir PowerShell en la raíz del proyecto.  
  2. Ejecutar: `.\generate-microservices-dashboard.ps1`

### ver-documentacion.ps1
- Qué hace: abre en el navegador la documentación Swagger de todos los microservicios.
- Cómo ejecutarlo:  
  1. Abrir PowerShell en la raíz del proyecto.  
  2. Ejecutar: `.\ver-documentacion.ps1`

### verificar-docs.ps1
- Qué hace: verifica si la documentación Swagger de cada microservicio responde y muestra un resumen en consola.
- Cómo ejecutarlo:  
  1. Abrir PowerShell en la raíz del proyecto.  
  2. Ejecutar: `.\verificar-docs.ps1`

## Permisos de ejecución en PowerShell
Si PowerShell bloquea la ejecución de scripts, habilita la política para el usuario actual:
```
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

## Flujo recomendado de uso
1) Levantar microservicios (con `start-services.ps1` u otro método).  
2) Ejecutar `generate-dashboard-and-docs.ps1` para verificar Swagger, generar el dashboard y abrir la documentación.  
