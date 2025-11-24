package com.fixsy.usuariosservice.service;

import com.fixsy.usuariosservice.dto.MechanicProfileRequest;
import com.fixsy.usuariosservice.dto.MechanicProfileResponse;
import com.fixsy.usuariosservice.entity.MechanicProfile;
import com.fixsy.usuariosservice.entity.User;
import com.fixsy.usuariosservice.repository.MechanicProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MechanicProfileService {

    @Autowired
    private MechanicProfileRepository mechanicProfileRepository;

    @Autowired
    private UserService userService;

    public MechanicProfileResponse create(MechanicProfileRequest request) {
        User user = userService.getEntity(request.getUserId());
        userService.ensureMechanicRole(user);

        if (mechanicProfileRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El perfil de mecánico ya existe");
        }

        MechanicProfile profile = new MechanicProfile();
        profile.setUser(user);
        profile.setSpecialty(request.getSpecialty());
        profile.setExperienceYears(request.getExperienceYears());
        profile.setPricePerHour(request.getPricePerHour());
        if (request.getIsAvailable() != null) {
            profile.setIsAvailable(request.getIsAvailable());
        }
        if (request.getAverageRating() != null) {
            profile.setAverageRating(request.getAverageRating());
        }

        return toResponse(mechanicProfileRepository.save(profile));
    }

    public MechanicProfileResponse getById(Long userId) {
        MechanicProfile profile = mechanicProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mecánico no encontrado"));
        return toResponse(profile);
    }

    public List<MechanicProfileResponse> listAll() {
        return mechanicProfileRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MechanicProfileResponse toResponse(MechanicProfile profile) {
        User user = profile.getUser();
        return MechanicProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .specialty(profile.getSpecialty())
                .experienceYears(profile.getExperienceYears())
                .pricePerHour(profile.getPricePerHour())
                .isAvailable(profile.getIsAvailable())
                .averageRating(profile.getAverageRating())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
