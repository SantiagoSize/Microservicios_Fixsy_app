package com.fixsy.usuariosservice.service;

import com.fixsy.usuariosservice.dto.LoginRequest;
import com.fixsy.usuariosservice.dto.LoginResponse;
import com.fixsy.usuariosservice.dto.UserRequest;
import com.fixsy.usuariosservice.dto.UserResponse;
import com.fixsy.usuariosservice.dto.UserUpdateRequest;
import com.fixsy.usuariosservice.entity.Role;
import com.fixsy.usuariosservice.entity.User;
import com.fixsy.usuariosservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // En producción, hashear la contraseña
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        return toResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return toResponse(user);
    }

    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        return toResponse(userRepository.save(user));
    }

    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public void ensureMechanicRole(User user) {
        if (user.getRole() != Role.MECHANIC) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene rol de mecánico");
        }
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
