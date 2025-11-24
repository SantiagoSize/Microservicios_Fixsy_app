package com.fixsy.usuariosservice.service;

import com.fixsy.usuariosservice.dto.LoginRequest;
import com.fixsy.usuariosservice.dto.UserRequest;
import com.fixsy.usuariosservice.dto.UserUpdateRequest;
import com.fixsy.usuariosservice.entity.Role;
import com.fixsy.usuariosservice.entity.User;
import com.fixsy.usuariosservice.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerShouldFailWhenEmailExists() {
        UserRequest request = new UserRequest();
        request.setName("Juan");
        request.setEmail("juan@example.com");
        request.setPassword("pwd");
        request.setRole(Role.CLIENT);

        when(userRepository.existsByEmail("juan@example.com")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.register(request));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    void loginShouldFailWithWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("wrong");

        User stored = new User();
        stored.setId(5L);
        stored.setEmail("user@example.com");
        stored.setPassword("secret");
        stored.setRole(Role.CLIENT);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(stored));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.login(request));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void updateShouldFailWhenEmailBelongsToAnotherUser() {
        User existing = new User();
        existing.setId(1L);
        existing.setEmail("old@example.com");
        existing.setName("Old");

        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("new@example.com");
        request.setName("Nuevo");
        request.setPhone("123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.update(1L, request));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    void getByIdShouldFailWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.getById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
