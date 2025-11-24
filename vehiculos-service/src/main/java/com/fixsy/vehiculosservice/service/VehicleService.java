package com.fixsy.vehiculosservice.service;

import com.fixsy.vehiculosservice.dto.VehicleRequest;
import com.fixsy.vehiculosservice.dto.VehicleResponse;
import com.fixsy.vehiculosservice.dto.VehicleUpdateRequest;
import com.fixsy.vehiculosservice.entity.Vehicle;
import com.fixsy.vehiculosservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleResponse create(VehicleRequest request) {
        if (vehicleRepository.existsByPlate(request.getPlate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La placa ya está registrada");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setUserId(request.getUserId());
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setPlate(request.getPlate());
        vehicle.setColor(request.getColor());
        vehicle.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));

        Vehicle saved = vehicleRepository.save(vehicle);
        if (Boolean.TRUE.equals(saved.getIsDefault())) {
            clearOtherDefaults(saved.getUserId(), saved.getId());
        }
        return toResponse(saved);
    }

    public List<VehicleResponse> findByUser(Long userId) {
        return vehicleRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado"));
        return toResponse(vehicle);
    }

    public VehicleResponse update(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado"));

        if (!vehicle.getPlate().equals(request.getPlate()) && vehicleRepository.existsByPlate(request.getPlate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La placa ya está registrada");
        }

        vehicle.setUserId(request.getUserId());
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setPlate(request.getPlate());
        vehicle.setColor(request.getColor());
        if (request.getIsDefault() != null) {
            vehicle.setIsDefault(request.getIsDefault());
        }

        Vehicle saved = vehicleRepository.save(vehicle);
        if (Boolean.TRUE.equals(saved.getIsDefault())) {
            clearOtherDefaults(saved.getUserId(), saved.getId());
        }
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado");
        }
        vehicleRepository.deleteById(id);
    }

    public VehicleResponse setDefault(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado"));
        vehicle.setIsDefault(true);
        Vehicle saved = vehicleRepository.save(vehicle);
        clearOtherDefaults(saved.getUserId(), saved.getId());
        return toResponse(saved);
    }

    private void clearOtherDefaults(Long userId, Long keepVehicleId) {
        vehicleRepository.findByUserId(userId).forEach(v -> {
            if (!v.getId().equals(keepVehicleId) && Boolean.TRUE.equals(v.getIsDefault())) {
                v.setIsDefault(false);
                vehicleRepository.save(v);
            }
        });
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .userId(vehicle.getUserId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .plate(vehicle.getPlate())
                .color(vehicle.getColor())
                .isDefault(vehicle.getIsDefault())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}
