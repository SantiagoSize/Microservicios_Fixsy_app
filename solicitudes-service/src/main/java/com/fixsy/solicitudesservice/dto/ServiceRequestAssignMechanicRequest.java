package com.fixsy.solicitudesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para asignar mecánico a la solicitud")
public class ServiceRequestAssignMechanicRequest {

    @NotNull
    @Schema(description = "ID del mecánico", example = "8")
    private Long mechanicId;
}
