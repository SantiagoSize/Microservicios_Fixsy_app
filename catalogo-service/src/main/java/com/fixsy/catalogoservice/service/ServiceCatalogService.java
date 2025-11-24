package com.fixsy.catalogoservice.service;

import com.fixsy.catalogoservice.dto.ServiceCreateRequest;
import com.fixsy.catalogoservice.dto.ServiceResponse;
import com.fixsy.catalogoservice.dto.ServiceUpdateRequest;
import com.fixsy.catalogoservice.entity.ServiceItem;
import com.fixsy.catalogoservice.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCatalogService {

    @Autowired
    private ServiceRepository serviceRepository;

    public ServiceResponse createService(ServiceCreateRequest request) {
        ServiceItem entity = new ServiceItem();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setBasePrice(request.getBasePrice());
        entity.setEstimatedTimeMinutes(request.getEstimatedTimeMinutes());
        return toResponse(serviceRepository.save(entity));
    }

    public ServiceResponse getServiceById(Long id) {
        ServiceItem entity = findOrThrow(id);
        return toResponse(entity);
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ServiceResponse updateService(Long id, ServiceUpdateRequest request) {
        ServiceItem entity = findOrThrow(id);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        if (request.getBasePrice() != null) {
            entity.setBasePrice(request.getBasePrice());
        }
        if (request.getEstimatedTimeMinutes() != null) {
            entity.setEstimatedTimeMinutes(request.getEstimatedTimeMinutes());
        }
        return toResponse(serviceRepository.save(entity));
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado");
        }
        serviceRepository.deleteById(id);
    }

    private ServiceItem findOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));
    }

    private ServiceResponse toResponse(ServiceItem entity) {
        return ServiceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .estimatedTimeMinutes(entity.getEstimatedTimeMinutes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
