package com.fixsy.vehiculosservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para crear vehiculo")
public class VehicleRequest {
    @Schema(description = "ID del usuario dueno del vehiculo", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Marca", example = "Toyota")
    @NotBlank
    @Size(max = 255)
    private String brand;

    @Schema(description = "Modelo", example = "Corolla")
    @NotBlank
    @Size(max = 255)
    private String model;

    @Schema(description = "Ano", example = "2021")
    @NotNull
    @Min(1886)
    @Max(2100)
    private Integer year;

    @Schema(description = "Placa", example = "ABC123")
    @NotBlank
    @Size(max = 100)
    private String plate;

    @Schema(description = "Color", example = "Rojo")
    @Size(max = 100)
    private String color;

    @Schema(description = "Indica si es el vehiculo predeterminado del usuario", example = "true")
    private Boolean isDefault;
}
