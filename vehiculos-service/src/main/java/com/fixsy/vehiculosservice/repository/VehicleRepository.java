package com.fixsy.vehiculosservice.repository;

import com.fixsy.vehiculosservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserId(Long userId);
    Optional<Vehicle> findByUserIdAndIsDefaultTrue(Long userId);
    boolean existsByPlate(String plate);
}
