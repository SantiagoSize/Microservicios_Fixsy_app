package com.fixsy.vehiculosservice.controller;

import com.fixsy.vehiculosservice.dto.VehicleRequest;
import com.fixsy.vehiculosservice.dto.VehicleResponse;
import com.fixsy.vehiculosservice.dto.VehicleUpdateRequest;
import com.fixsy.vehiculosservice.service.VehicleService;
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
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "Operaciones sobre vehículos")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    @Operation(summary = "Crear vehículo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehículo creado"),
            @ApiResponse(responseCode = "409", description = "Placa duplicada", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        return new ResponseEntity<>(vehicleService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar vehículos por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<List<VehicleResponse>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(vehicleService.findByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vehículo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<VehicleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vehículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Placa duplicada", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id, @Valid @RequestBody VehicleUpdateRequest request) {
        return ResponseEntity.ok(vehicleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vehículo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.vehiculosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
