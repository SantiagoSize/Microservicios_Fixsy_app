package com.fixsy.solicitudesservice.service;

import com.fixsy.solicitudesservice.dto.ServiceRequestStatusUpdateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestUpdateRequest;
import com.fixsy.solicitudesservice.entity.ServiceRequest;
import com.fixsy.solicitudesservice.entity.ServiceRequestStatus;
import com.fixsy.solicitudesservice.repository.ServiceRequestRepository;
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
class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepository serviceRequestRepository;

    @InjectMocks
    private ServiceRequestService serviceRequestService;

    @Test
    void updateStatusShouldFailWithInvalidStatus() {
        ServiceRequestStatusUpdateRequest request = new ServiceRequestStatusUpdateRequest();
        request.setStatus("Estado Desconocido");

        ServiceRequest stored = new ServiceRequest();
        stored.setId(3L);
        stored.setStatus(ServiceRequestStatus.Pendiente);

        when(serviceRequestRepository.findById(3L)).thenReturn(Optional.of(stored));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> serviceRequestService.updateStatus(3L, request));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void updateShouldModifyOnlyProvidedFields() {
        ServiceRequest stored = new ServiceRequest();
        stored.setId(4L);
        stored.setServiceType("Original");
        stored.setDescription("Desc");
        stored.setEstimatedCost("100");
        stored.setStatus(ServiceRequestStatus.Pendiente);

        ServiceRequestUpdateRequest request = new ServiceRequestUpdateRequest();
        request.setDescription("Nueva desc");
        request.setEstimatedCost("500");

        when(serviceRequestRepository.findById(4L)).thenReturn(Optional.of(stored));
        when(serviceRequestRepository.save(any(ServiceRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = serviceRequestService.update(4L, request);

        assertEquals("Original", response.getServiceType());
        assertEquals("Nueva desc", response.getDescription());
        assertEquals("500", response.getEstimatedCost());
        verify(serviceRequestRepository).save(stored);
    }
}
