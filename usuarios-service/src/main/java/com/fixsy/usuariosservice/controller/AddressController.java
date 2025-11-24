package com.fixsy.usuariosservice.controller;

import com.fixsy.usuariosservice.dto.AddressRequest;
import com.fixsy.usuariosservice.dto.AddressResponse;
import com.fixsy.usuariosservice.service.AddressService;
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
@RequestMapping("/addresses")
@Tag(name = "Addresses", description = "Operaciones de direcciones")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar direcciones por usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Direcciones listadas"),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<List<AddressResponse>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.findByUser(userId));
    }

    @PostMapping
    @Operation(summary = "Crear dirección")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dirección creada"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<AddressResponse> create(@Valid @RequestBody AddressRequest request) {
        return new ResponseEntity<>(addressService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dirección por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dirección eliminada"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
