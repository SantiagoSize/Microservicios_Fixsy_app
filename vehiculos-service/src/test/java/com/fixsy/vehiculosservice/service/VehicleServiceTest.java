package com.fixsy.vehiculosservice.service;

import com.fixsy.vehiculosservice.dto.VehicleRequest;
import com.fixsy.vehiculosservice.repository.VehicleRepository;
import com.fixsy.vehiculosservice.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void createShouldFailWhenPlateExists() {
        VehicleRequest request = new VehicleRequest();
        request.setUserId(1L);
        request.setBrand("Toyota");
        request.setModel("Corolla");
        request.setYear(2020);
        request.setPlate("ABC123");

        when(vehicleRepository.existsByPlate("ABC123")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> vehicleService.create(request));
        assertTrue(ex.getStatusCode().isSameCodeAs(HttpStatus.CONFLICT));
    }

    @Test
    void createDefaultShouldClearPreviousDefaultForUser() {
        VehicleRequest request = new VehicleRequest();
        request.setUserId(10L);
        request.setBrand("Tesla");
        request.setModel("Model 3");
        request.setYear(2024);
        request.setPlate("EV123");
        request.setIsDefault(true);

        Vehicle existingDefault = new Vehicle();
        existingDefault.setId(1L);
        existingDefault.setUserId(10L);
        existingDefault.setIsDefault(true);

        AtomicLong ids = new AtomicLong(1);
        when(vehicleRepository.existsByPlate("EV123")).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle v = invocation.getArgument(0);
            if (v.getId() == null) {
                v.setId(ids.incrementAndGet());
            }
            return v;
        });
        when(vehicleRepository.findByUserId(10L)).thenReturn(List.of(existingDefault));

        var response = vehicleService.create(request);

        assertTrue(response.getIsDefault());
        assertFalse(existingDefault.getIsDefault());
        verify(vehicleRepository).save(existingDefault);
    }
}
