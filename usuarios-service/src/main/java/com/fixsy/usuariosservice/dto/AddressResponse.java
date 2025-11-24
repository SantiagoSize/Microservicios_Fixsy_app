package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Respuesta de dirección")
public class AddressResponse {
    @Schema(description = "ID de la dirección", example = "5")
    private Long id;

    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    private String name;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
