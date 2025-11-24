package com.fixsy.catalogoservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de servicio")
public class ServiceResponse {

    private Long id;
    private String name;
    private String description;
    private Integer basePrice;
    private Integer estimatedTimeMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
