package com.fixsy.catalogoservice.service;

import com.fixsy.catalogoservice.dto.ServiceUpdateRequest;
import com.fixsy.catalogoservice.entity.ServiceItem;
import com.fixsy.catalogoservice.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceCatalogServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceCatalogService serviceCatalogService;

    @Test
    void updateShouldFailWhenServiceDoesNotExist() {
        ServiceUpdateRequest request = new ServiceUpdateRequest();
        request.setName("Nuevo");
        request.setDescription("Desc");

        when(serviceRepository.findById(5L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> serviceCatalogService.updateService(5L, request));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void updateShouldKeepExistingValuesWhenFieldsAreNull() {
        ServiceItem stored = new ServiceItem();
        stored.setId(2L);
        stored.setName("Basico");
        stored.setDescription("Desc");
        stored.setBasePrice(100);
        stored.setEstimatedTimeMinutes(60);

        ServiceUpdateRequest request = new ServiceUpdateRequest();
        request.setName("Full");
        request.setDescription("Detalle");
        request.setEstimatedTimeMinutes(90);

        when(serviceRepository.findById(2L)).thenReturn(Optional.of(stored));
        when(serviceRepository.save(any(ServiceItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = serviceCatalogService.updateService(2L, request);

        assertEquals("Full", response.getName());
        assertEquals("Detalle", response.getDescription());
        assertEquals(100, response.getBasePrice());
        assertEquals(90, response.getEstimatedTimeMinutes());
        verify(serviceRepository).save(stored);
    }
}
