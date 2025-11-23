# Uso de los microservicios (Windows/PowerShell)

## Requisitos
- Java 21 instalado y en PATH (`java -version`).
- Maven disponible:
  - Con Chocolatey (PowerShell como administrador):  
    `choco install maven -y`
  - Verificar: `mvn -v`
  - Alternativa manual: descomprimir Maven en `C:\apache-maven-3.9.x`, añadir `C:\apache-maven-3.9.x\bin` al PATH y validar con `mvn -v`.
- Permitir scripts en la sesión si hay bloqueo:  
  `Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass`

## Estructura de microservicios en este repo
- `usuarios`            (puerto 8081)
- `gestionsolicitudes`  (puerto 8082)
- `vehiculos`           (puerto 8085)

## Instrucciones de uso (paso a paso)
1) Abre PowerShell en la raíz del proyecto.
2) Opcional: habilita scripts en la sesión actual si te los bloquea:  
   `Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass`
3) Inicia los microservicios (abre una ventana por servicio):  
   `.\start-services.ps1`
4) Verifica Swagger, genera el dashboard y abre la documentación:  
   `.\generate-dashboard-and-docs.ps1`
5) Comprobar Maven en PATH (si es necesario):  
   `mvn -v`

## Swagger de cada servicio
- Usuarios:            http://localhost:8081/swagger-ui/index.html
- Gestión Solicitudes: http://localhost:8082/swagger-ui/index.html
- Vehículos:           http://localhost:8085/swagger-ui/index.html

## Notas
- Ejecuta los scripts desde la raíz del proyecto.
- Si el script indica que no encuentra `mvn`, instala Maven o añade el wrapper `mvnw/mvnw.cmd` en cada microservicio.
