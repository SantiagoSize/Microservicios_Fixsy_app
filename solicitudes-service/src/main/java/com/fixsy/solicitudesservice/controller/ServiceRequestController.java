package com.fixsy.solicitudesservice.controller;

import com.fixsy.solicitudesservice.dto.ErrorResponse;
import com.fixsy.solicitudesservice.dto.ServiceRequestAssignMechanicRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestCreateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestResponse;
import com.fixsy.solicitudesservice.dto.ServiceRequestStatusUpdateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestUpdateRequest;
import com.fixsy.solicitudesservice.service.ServiceRequestService;
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
@RequestMapping("/requests")
@Tag(name = "Service Requests", description = "Operaciones sobre solicitudes de servicio")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping
    @Operation(summary = "Crear solicitud de servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Solicitud creada"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceRequestResponse> create(@Valid @RequestBody ServiceRequestCreateRequest request) {
        return new ResponseEntity<>(serviceRequestService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener solicitud por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceRequestResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRequestService.getById(id));
    }

    @GetMapping("/user/{clientId}")
    @Operation(summary = "Listar solicitudes por cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitudes listadas"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ServiceRequestResponse>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(serviceRequestService.findByClient(clientId));
    }

    @GetMapping("/mechanic/{mechanicId}")
    @Operation(summary = "Listar solicitudes por mecánico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitudes listadas"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ServiceRequestResponse>> getByMechanic(@PathVariable Long mechanicId) {
        return ResponseEntity.ok(serviceRequestService.findByMechanic(mechanicId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar solicitud")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceRequestResponse> update(@PathVariable Long id, @Valid @RequestBody ServiceRequestUpdateRequest request) {
        return ResponseEntity.ok(serviceRequestService.update(id, request));
    }

    @PutMapping("/{id}/assign")
    @Operation(summary = "Asignar mecánico a la solicitud")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mecánico asignado"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceRequestResponse> assignMechanic(@PathVariable Long id, @Valid @RequestBody ServiceRequestAssignMechanicRequest request) {
        return ResponseEntity.ok(serviceRequestService.assignMechanic(id, request));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de la solicitud")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Estado inválido o error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceRequestResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody ServiceRequestStatusUpdateRequest request) {
        return ResponseEntity.ok(serviceRequestService.updateStatus(id, request));
    }
}
