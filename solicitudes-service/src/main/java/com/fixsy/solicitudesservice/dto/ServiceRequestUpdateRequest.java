package com.fixsy.solicitudesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para actualizar datos generales de la solicitud")
public class ServiceRequestUpdateRequest {

    @Size(max = 255)
    @Schema(description = "Tipo de servicio", example = "Revisión general")
    private String serviceType;

    @Schema(description = "Descripción de la solicitud", example = "Revisar frenos y suspensión")
    private String description;

    @Size(max = 255)
    @Schema(description = "Ubicación", example = "Avenida 7 #100-20")
    private String location;

    @Schema(description = "JSON de URLs de imágenes", example = "[\"https://.../2.jpg\"]")
    private String images;

    @Size(max = 255)
    @Schema(description = "Costo estimado", example = "250.00")
    private String estimatedCost;

    @Schema(description = "Notas adicionales", example = "Cliente requiere factura")
    private String notes;

    @Schema(description = "ID del vehículo", example = "15")
    private Long vehicleId;
}
