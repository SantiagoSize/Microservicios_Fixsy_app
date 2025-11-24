package com.fixsy.vehiculosservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixsy.vehiculosservice.dto.VehicleRequest;
import com.fixsy.vehiculosservice.dto.VehicleResponse;
import com.fixsy.vehiculosservice.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void createShouldReturn201() throws Exception {
        VehicleResponse response = VehicleResponse.builder()
                .id(1L)
                .userId(10L)
                .brand("Toyota")
                .model("Corolla")
                .year(2020)
                .plate("ABC123")
                .isDefault(true)
                .build();
        Mockito.when(vehicleService.create(any(VehicleRequest.class))).thenReturn(response);

        VehicleRequest request = new VehicleRequest();
        request.setUserId(10L);
        request.setBrand("Toyota");
        request.setModel("Corolla");
        request.setYear(2020);
        request.setPlate("ABC123");

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.plate", is("ABC123")));
    }

    @Test
    void listByUserShouldReturn200WithList() throws Exception {
        VehicleResponse v1 = VehicleResponse.builder().id(1L).userId(5L).plate("AAA111").build();
        VehicleResponse v2 = VehicleResponse.builder().id(2L).userId(5L).plate("BBB222").build();
        Mockito.when(vehicleService.findByUser(5L)).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/vehicles/user/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].plate", is("AAA111")));
    }
}
