<#
 start-services.ps1 – versión hermética sin comillas internas
#>

$services = @(
    @{ Name = "usuarios";           Port = 8081 },
    @{ Name = "gestionsolicitudes"; Port = 8082 },
    @{ Name = "vehiculos";          Port = 8085 }
)

$baseDir = if ($PSScriptRoot) { $PSScriptRoot } else { Get-Location }

foreach ($svc in $services) {

    $name = $svc.Name
    $port = $svc.Port
    $path = Join-Path -Path $baseDir -ChildPath $name

    if (-not (Test-Path $path)) {
        Write-Warning ("Carpeta no encontrada: " + $name)
        continue
    }

    # BLOQUE SIN COMILLAS INTERNAS -> SEGURO
    $cmd = @"
cd $path
Write-Host Iniciando_$name_en_el_puerto_$port... -ForegroundColor Cyan

if (Test-Path .\mvnw) {
    Write-Host Ejecutando_mvnw_spring-boot:run -ForegroundColor Green
    & .\mvnw spring-boot:run
}
elseif (Test-Path .\mvnw.cmd) {
    Write-Host Ejecutando_mvnw.cmd_spring-boot:run -ForegroundColor Green
    & .\mvnw.cmd spring-boot:run
}
elseif (Get-Command mvn -ErrorAction SilentlyContinue) {
    Write-Host Ejecutando_mvn_spring-boot:run -ForegroundColor Yellow
    mvn spring-boot:run
}
else {
    Write-Warning No_se_encontro_mvnw_ni_mvn_en_PATH_No_se_puede_iniciar_$name
}

Write-Host Microservicio_finalizo_$name_puerto_$port -ForegroundColor Green
Write-Host Presiona_ENTER_para_cerrar... -ForegroundColor Gray
Read-Host
"@

    Start-Process powershell -ArgumentList "-NoExit", "-Command", $cmd
}

