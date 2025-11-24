-- Seed idempotente: usa INSERT IGNORE para no borrar datos previos.
INSERT IGNORE INTO services (id, name, description, base_price, estimated_time_minutes)
VALUES
(1, 'Mantenimiento básico', 'Cambio de aceite, filtro de aceite y revisión visual', 50000, 60),
(2, 'Mantenimiento completo', 'Aceite, filtros, revisión de frenos y suspensión', 120000, 120),
(3, 'Cambio de pastillas de freno', 'Incluye mano de obra y ajuste de frenos', 80000, 90),
(4, 'Diagnóstico general', 'Escaneo y revisión general del vehículo', 40000, 45);
