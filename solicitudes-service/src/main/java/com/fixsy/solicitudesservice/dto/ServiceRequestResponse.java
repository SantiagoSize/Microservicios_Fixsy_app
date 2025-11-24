package com.fixsy.solicitudesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de solicitud de servicio")
public class ServiceRequestResponse {

    private Long id;
    private Long clientId;
    private Long mechanicId;
    private Long vehicleId;
    private String status;
    private String serviceType;
    private String description;
    private String location;
    private String images;
    private String estimatedCost;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
