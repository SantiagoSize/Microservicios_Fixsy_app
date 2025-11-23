<#
 generate-dashboard-and-docs.ps1
 - Verifica Swagger, genera un dashboard HTML de estado y abre la documentación Swagger de todos los microservicios.
 - Cómo ejecutar: abre PowerShell en la raíz del proyecto y corre: .\generate-dashboard-and-docs.ps1
 - Si hay restricciones de permisos: Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
#>

# Definición de microservicios y rutas de Swagger
$services = @(
    @{ Name = "usuarios";           Port = 8081 },
    @{ Name = "gestionsolicitudes"; Port = 8082 },
    @{ Name = "vehiculos";          Port = 8085 }
)

$baseDir = if ($PSScriptRoot) { $PSScriptRoot } else { Get-Location }
$script:serviceStatus = @()

function Check-SwaggerStatus {
    <#
     Verifica si Swagger responde para cada microservicio.
     Muestra ✔/❌ en consola y guarda el estado en $script:serviceStatus.
    #>
    $script:serviceStatus = @()
    foreach ($svc in $services) {
        $url = "http://localhost:{0}/swagger-ui/index.html" -f $svc.Port
        $status = "Offline"
        try {
            $resp = Invoke-WebRequest -Uri $url -Method Get -TimeoutSec 2 -ErrorAction Stop
            if ($resp.StatusCode -ge 200 -and $resp.StatusCode -lt 400) {
                $status = "Online"
                Write-Host "✔ Swagger OK → $($svc.Name)" -ForegroundColor Green
            } else {
                Write-Host "❌ NO responde → $($svc.Name)" -ForegroundColor Red
            }
        } catch {
            Write-Host "❌ NO responde → $($svc.Name)" -ForegroundColor Red
        }
        $script:serviceStatus += [PSCustomObject]@{
            Name   = $svc.Name
            Port   = $svc.Port
            Url    = $url
            Status = $status
        }
    }
}

function Open-AllSwagger {
    <#
     Abre en el navegador la documentación Swagger de todos los microservicios.
    #>
    foreach ($svc in $services) {
        $url = "http://localhost:{0}/swagger-ui/index.html" -f $svc.Port
        Write-Host "Abriendo Swagger de $($svc.Name)..." -ForegroundColor Cyan
        try {
            Start-Process $url
        } catch {
            Write-Warning ("No se pudo abrir {0}: {1}" -f $url, $_.Exception.Message)
        }
    }
}

function Generate-Dashboard {
    <#
     Genera microservices-dashboard.html con el estado Online/Offline y lo abre en el navegador.
    #>
    if (-not $script:serviceStatus -or $script:serviceStatus.Count -eq 0) {
        Check-SwaggerStatus
    }

    $online = ($script:serviceStatus | Where-Object { $_.Status -eq "Online" }).Count
    $offline = ($script:serviceStatus | Where-Object { $_.Status -ne "Online" }).Count

    $rows = foreach ($s in $script:serviceStatus) {
        $stateClass = if ($s.Status -eq "Online") { "online" } else { "offline" }
        "<tr><td>$($s.Name)</td><td>$($s.Port)</td><td class='state $stateClass'>$($s.Status)</td><td><a href='$($s.Url)' target='_blank'>Swagger</a></td></tr>"
    }

    $timestamp = (Get-Date).ToString("yyyy-MM-dd HH:mm:ss")
    $html = @"
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="10">
    <title>Dashboard de Microservicios</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: radial-gradient(circle at 20% 20%, #1f2937, #0f172a 60%); color: #e2e8f0; margin: 0; padding: 24px; }
        h1 { margin: 0 0 8px 0; font-size: 28px; }
        h2 { margin: 0 0 20px 0; font-size: 15px; font-weight: 500; color: #94a3b8; }
        .summary { display: flex; gap: 12px; margin-bottom: 18px; }
        .badge { padding: 10px 14px; border-radius: 12px; font-weight: 700; color: #0f172a; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
        .badge-online { background: linear-gradient(135deg, #22c55e, #16a34a); }
        .badge-offline { background: linear-gradient(135deg, #ef4444, #b91c1c); }
        table { width: 100%; border-collapse: collapse; background: rgba(30, 41, 59, 0.85); border-radius: 14px; overflow: hidden; box-shadow: 0 16px 40px rgba(0,0,0,0.35); }
        th, td { padding: 14px 16px; text-align: left; }
        th { background: rgba(51, 65, 85, 0.8); color: #cbd5e1; text-transform: uppercase; letter-spacing: 0.02em; font-size: 12px; }
        tr { transition: background 0.2s ease; }
        tr:nth-child(odd) { background: rgba(31, 41, 55, 0.8); }
        tr:nth-child(even) { background: rgba(38, 50, 69, 0.8); }
        tr:hover { background: rgba(56, 189, 248, 0.08); }
        .state { font-weight: 800; letter-spacing: 0.01em; transition: transform 0.2s ease, color 0.2s ease; }
        .online { color: #22c55e; }
        .offline { color: #ef4444; }
        .state:hover { transform: scale(1.04); }
        a { color: #38bdf8; text-decoration: none; font-weight: 600; }
        a:hover { text-decoration: underline; }
        .footer { margin-top: 16px; color: #94a3b8; font-size: 14px; }
    </style>
</head>
<body>
    <h1>Dashboard de Microservicios</h1>
    <h2>Auto-refresco cada 10 segundos</h2>
    <div class="summary">
        <div class="badge badge-online">Online: $online</div>
        <div class="badge badge-offline">Offline: $offline</div>
    </div>
    <table>
        <thead>
            <tr>
                <th>Servicio</th>
                <th>Puerto</th>
                <th>Estado</th>
                <th>Swagger</th>
            </tr>
        </thead>
        <tbody>
            $($rows -join "`n")
        </tbody>
    </table>
    <div class="footer">Última actualización: $timestamp</div>
</body>
</html>
"@

    $outputPath = Join-Path -Path $baseDir -ChildPath "microservices-dashboard.html"
    Set-Content -Path $outputPath -Value $html -Encoding UTF8
    Start-Process $outputPath
}

# Ejecución: primero verifica, luego genera dashboard, luego abre todos los Swagger
Check-SwaggerStatus
Generate-Dashboard
Open-AllSwagger
