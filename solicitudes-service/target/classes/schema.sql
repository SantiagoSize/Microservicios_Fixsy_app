CREATE TABLE IF NOT EXISTS service_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    mechanic_id BIGINT NULL,
    vehicle_id BIGINT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    service_type VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(255),
    images TEXT NULL,
    estimated_cost VARCHAR(255) NULL,
    notes TEXT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
