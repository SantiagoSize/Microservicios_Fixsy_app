package com.fixsy.catalogoservice.repository;

import com.fixsy.catalogoservice.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceItem, Long> {
}
