package com.fixsy.catalogoservice.controller;

import com.fixsy.catalogoservice.dto.ServiceResponse;
import com.fixsy.catalogoservice.service.ServiceCatalogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCatalogService serviceCatalogService;

    @Test
    void findAllShouldReturnList() throws Exception {
        ServiceResponse s1 = ServiceResponse.builder().id(1L).name("A").description("d").build();
        ServiceResponse s2 = ServiceResponse.builder().id(2L).name("B").description("d2").build();
        Mockito.when(serviceCatalogService.getAllServices()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("B")));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mockMvc.perform(delete("/services/9"))
                .andExpect(status().isNoContent());
        Mockito.verify(serviceCatalogService).deleteService(9L);
    }
}
