package com.fixsy.vehiculosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de vehículo")
public class VehicleResponse {
    @Schema(description = "ID del vehículo", example = "15")
    private Long id;

    @Schema(description = "ID del usuario dueño", example = "1")
    private Long userId;

    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private String color;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
