package com.fixsy.solicitudesservice.repository;

import com.fixsy.solicitudesservice.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByClientId(Long clientId);
    List<ServiceRequest> findByMechanicId(Long mechanicId);
}
