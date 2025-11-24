package com.fixsy.reviewsservice.controller;

import com.fixsy.reviewsservice.dto.ErrorResponse;
import com.fixsy.reviewsservice.dto.ReviewCreateRequest;
import com.fixsy.reviewsservice.dto.ReviewResponse;
import com.fixsy.reviewsservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Operaciones sobre reseñas")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Crear reseña")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reseña creada"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody ReviewCreateRequest request) {
        return new ResponseEntity<>(reviewService.createReview(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReviewResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @GetMapping("/mechanic/{mechanicId}")
    @Operation(summary = "Listar reseñas por mecánico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ReviewResponse>> getByMechanic(@PathVariable Long mechanicId) {
        return ResponseEntity.ok(reviewService.getReviewsByMechanic(mechanicId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar reseñas por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ReviewResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reseña eliminada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
