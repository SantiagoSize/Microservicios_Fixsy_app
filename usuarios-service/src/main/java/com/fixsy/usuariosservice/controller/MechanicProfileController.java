package com.fixsy.usuariosservice.controller;

import com.fixsy.usuariosservice.dto.MechanicProfileRequest;
import com.fixsy.usuariosservice.dto.MechanicProfileResponse;
import com.fixsy.usuariosservice.service.MechanicProfileService;
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
@RequestMapping("/mechanics")
@Tag(name = "Mechanics", description = "Operaciones de mecánicos")
public class MechanicProfileController {

    @Autowired
    private MechanicProfileService mechanicProfileService;

    @GetMapping
    @Operation(summary = "Listar mecánicos")
    public ResponseEntity<List<MechanicProfileResponse>> list() {
        return ResponseEntity.ok(mechanicProfileService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mecánico por ID de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mecánico encontrado"),
            @ApiResponse(responseCode = "404", description = "Mecánico no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<MechanicProfileResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(mechanicProfileService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crear perfil de mecánico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Perfil creado"),
            @ApiResponse(responseCode = "400", description = "Usuario no es mecánico", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Perfil ya existe", content = @Content(schema = @Schema(implementation = com.fixsy.usuariosservice.dto.ErrorResponse.class)))
    })
    public ResponseEntity<MechanicProfileResponse> create(@Valid @RequestBody MechanicProfileRequest request) {
        return new ResponseEntity<>(mechanicProfileService.create(request), HttpStatus.CREATED);
    }
}
