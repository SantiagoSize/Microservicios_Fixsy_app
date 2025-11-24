package com.fixsy.catalogoservice.controller;

import com.fixsy.catalogoservice.dto.ErrorResponse;
import com.fixsy.catalogoservice.dto.ServiceCreateRequest;
import com.fixsy.catalogoservice.dto.ServiceResponse;
import com.fixsy.catalogoservice.dto.ServiceUpdateRequest;
import com.fixsy.catalogoservice.service.ServiceCatalogService;
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
@RequestMapping("/services")
@Tag(name = "Services", description = "Operaciones sobre catálogo de servicios")
public class ServiceController {

    @Autowired
    private ServiceCatalogService serviceCatalogService;

    @PostMapping
    @Operation(summary = "Crear servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Servicio creado"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceCreateRequest request) {
        return new ResponseEntity<>(serviceCatalogService.createService(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar servicios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto")
    })
    public ResponseEntity<List<ServiceResponse>> findAll() {
        return ResponseEntity.ok(serviceCatalogService.getAllServices());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCatalogService.getServiceById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio actualizado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @Valid @RequestBody ServiceUpdateRequest request) {
        return ResponseEntity.ok(serviceCatalogService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Servicio eliminado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceCatalogService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
