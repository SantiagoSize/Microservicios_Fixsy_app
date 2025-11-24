package com.fixsy.usuariosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para crear dirección")
public class AddressRequest {
    @Schema(description = "ID del usuario", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Nombre de la dirección", example = "Casa")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String name;

    @Schema(description = "Dirección", example = "Calle 123 #45-67")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String address;

    @Schema(description = "Ciudad", example = "Bogotá")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String city;

    @Schema(description = "Región", example = "Cundinamarca")
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String region;

    @Schema(description = "Código postal", example = "110111")
    @jakarta.validation.constraints.Size(max = 50)
    private String postalCode;
}
