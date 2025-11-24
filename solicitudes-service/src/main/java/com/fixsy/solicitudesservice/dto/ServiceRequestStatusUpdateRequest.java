package com.fixsy.solicitudesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Payload para actualizar estado de la solicitud")
public class ServiceRequestStatusUpdateRequest {

    @NotBlank
    @Schema(description = "Estado", example = "En Proceso")
    private String status;
}
