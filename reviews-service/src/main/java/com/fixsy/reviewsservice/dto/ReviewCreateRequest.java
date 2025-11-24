package com.fixsy.reviewsservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para crear reseña")
public class ReviewCreateRequest {

    @NotNull
    @Schema(description = "ID del mecánico reseñado", example = "10")
    private Long mechanicId;

    @NotNull
    @Schema(description = "ID del usuario que realiza la reseña", example = "3")
    private Long userId;

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "Calificación 1-5", example = "5")
    private Integer rating;

    @NotBlank
    @Schema(description = "Comentario de la reseña", example = "Excelente servicio, muy puntual")
    private String comment;
}
