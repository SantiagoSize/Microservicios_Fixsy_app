package com.fixsy.usuariosservice.service;

import com.fixsy.usuariosservice.dto.AddressRequest;
import com.fixsy.usuariosservice.dto.AddressResponse;
import com.fixsy.usuariosservice.entity.Address;
import com.fixsy.usuariosservice.entity.User;
import com.fixsy.usuariosservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public List<AddressResponse> findByUser(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AddressResponse create(AddressRequest request) {
        User user = userService.getEntity(request.getUserId());

        Address address = new Address();
        address.setUser(user);
        address.setName(request.getName());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setRegion(request.getRegion());
        address.setPostalCode(request.getPostalCode());

        return toResponse(addressRepository.save(address));
    }

    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dirección no encontrada");
        }
        addressRepository.deleteById(id);
    }

    private AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .userId(address.getUser().getId())
                .name(address.getName())
                .address(address.getAddress())
                .city(address.getCity())
                .region(address.getRegion())
                .postalCode(address.getPostalCode())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}
