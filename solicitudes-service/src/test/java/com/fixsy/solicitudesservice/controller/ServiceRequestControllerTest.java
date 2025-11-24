package com.fixsy.solicitudesservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixsy.solicitudesservice.dto.ServiceRequestCreateRequest;
import com.fixsy.solicitudesservice.dto.ServiceRequestResponse;
import com.fixsy.solicitudesservice.dto.ServiceRequestStatusUpdateRequest;
import com.fixsy.solicitudesservice.service.ServiceRequestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceRequestController.class)
class ServiceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceRequestService serviceRequestService;

    @Test
    void createShouldReturn201() throws Exception {
        ServiceRequestResponse response = ServiceRequestResponse.builder()
                .id(1L)
                .clientId(2L)
                .serviceType("Reparacion")
                .build();
        Mockito.when(serviceRequestService.create(any(ServiceRequestCreateRequest.class))).thenReturn(response);

        ServiceRequestCreateRequest request = new ServiceRequestCreateRequest();
        request.setClientId(2L);
        request.setVehicleId(3L);
        request.setServiceType("Reparacion");
        request.setDescription("Falla motor");
        request.setLocation("Calle 123");

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void updateStatusShouldReturn200() throws Exception {
        ServiceRequestResponse response = ServiceRequestResponse.builder()
                .id(5L)
                .status("En Proceso")
                .build();
        Mockito.when(serviceRequestService.updateStatus(any(Long.class), any(ServiceRequestStatusUpdateRequest.class)))
                .thenReturn(response);

        ServiceRequestStatusUpdateRequest request = new ServiceRequestStatusUpdateRequest();
        request.setStatus("En Proceso");

        mockMvc.perform(put("/requests/5/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("En Proceso")));
    }
}
