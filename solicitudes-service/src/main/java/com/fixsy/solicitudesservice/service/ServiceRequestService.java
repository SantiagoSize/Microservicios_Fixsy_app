package com.fixsy.solicitudesservice.service;

import com.fixsy.solicitudesservice.dto.ServiceRequestAssignMechanicRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestCreateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestResponse;
import com.fixsy.solicitudesservice.dto.ServiceRequestStatusUpdateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestUpdateRequest;
import com.fixsy.solicitudesservice.entity.ServiceRequest;
import com.fixsy.solicitudesservice.entity.ServiceRequestStatus;
import com.fixsy.solicitudesservice.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestResponse create(ServiceRequestCreateRequest request) {
        ServiceRequest entity = new ServiceRequest();
        entity.setClientId(request.getClientId());
        entity.setMechanicId(request.getMechanicId());
        entity.setVehicleId(request.getVehicleId());
        entity.setServiceType(request.getServiceType());
        entity.setDescription(request.getDescription());
        entity.setLocation(request.getLocation());
        entity.setImages(request.getImages());
        entity.setEstimatedCost(request.getEstimatedCost());
        entity.setNotes(request.getNotes());
        entity.setStatus(ServiceRequestStatus.Pendiente);

        return toResponse(serviceRequestRepository.save(entity));
    }

    public ServiceRequestResponse getById(Long id) {
        ServiceRequest entity = findOrThrow(id);
        return toResponse(entity);
    }

    public List<ServiceRequestResponse> findByClient(Long clientId) {
        return serviceRequestRepository.findByClientId(clientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceRequestResponse> findByMechanic(Long mechanicId) {
        return serviceRequestRepository.findByMechanicId(mechanicId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ServiceRequestResponse update(Long id, ServiceRequestUpdateRequest request) {
        ServiceRequest entity = findOrThrow(id);

        if (request.getServiceType() != null) {
            entity.setServiceType(request.getServiceType());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getLocation() != null) {
            entity.setLocation(request.getLocation());
        }
        if (request.getImages() != null) {
            entity.setImages(request.getImages());
        }
        if (request.getEstimatedCost() != null) {
            entity.setEstimatedCost(request.getEstimatedCost());
        }
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
        if (request.getVehicleId() != null) {
            entity.setVehicleId(request.getVehicleId());
        }

        return toResponse(serviceRequestRepository.save(entity));
    }

    public ServiceRequestResponse assignMechanic(Long id, ServiceRequestAssignMechanicRequest request) {
        ServiceRequest entity = findOrThrow(id);
        entity.setMechanicId(request.getMechanicId());
        return toResponse(serviceRequestRepository.save(entity));
    }

    public ServiceRequestResponse updateStatus(Long id, ServiceRequestStatusUpdateRequest request) {
        ServiceRequest entity = findOrThrow(id);
        entity.setStatus(parseStatus(request.getStatus()));
        return toResponse(serviceRequestRepository.save(entity));
    }

    private ServiceRequest findOrThrow(Long id) {
        return serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
    }

    private ServiceRequestStatus parseStatus(String status) {
        String normalized = status.replace(" ", "_");
        try {
            return ServiceRequestStatus.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido");
        }
    }

    private ServiceRequestResponse toResponse(ServiceRequest entity) {
        return ServiceRequestResponse.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .mechanicId(entity.getMechanicId())
                .vehicleId(entity.getVehicleId())
                .status(entity.getStatus().name().replace("_", " "))
                .serviceType(entity.getServiceType())
                .description(entity.getDescription())
                .location(entity.getLocation())
                .images(entity.getImages())
                .estimatedCost(entity.getEstimatedCost())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
