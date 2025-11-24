package com.fixsy.usuariosservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixsy.usuariosservice.dto.LoginRequest;
import com.fixsy.usuariosservice.dto.LoginResponse;
import com.fixsy.usuariosservice.dto.UserRequest;
import com.fixsy.usuariosservice.dto.UserResponse;
import com.fixsy.usuariosservice.entity.Role;
import com.fixsy.usuariosservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void registerShouldReturn201WithBody() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("Juan")
                .email("juan@example.com")
                .phone("123")
                .role(Role.CLIENT.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(userService.register(any(UserRequest.class))).thenReturn(response);

        UserRequest request = new UserRequest();
        request.setName("Juan");
        request.setEmail("juan@example.com");
        request.setPassword("pwd");
        request.setRole(Role.CLIENT);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("juan@example.com")))
                .andExpect(jsonPath("$.role", is("CLIENT")));
    }

    @Test
    void loginShouldReturn200() throws Exception {
        LoginResponse response = new LoginResponse(2L, "Ana", "ana@example.com", "ADMIN");
        Mockito.when(userService.login(any(LoginRequest.class))).thenReturn(response);

        LoginRequest request = new LoginRequest();
        request.setEmail("ana@example.com");
        request.setPassword("pwd");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(2)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    void getUserShouldReturn200() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(3L)
                .name("Luis")
                .email("luis@example.com")
                .role("MECHANIC")
                .build();
        Mockito.when(userService.getById(3L)).thenReturn(response);

        mockMvc.perform(get("/users/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.email", is("luis@example.com")));
    }
}
