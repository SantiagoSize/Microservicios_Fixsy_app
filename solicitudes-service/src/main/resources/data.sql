-- Seed idempotente: usa INSERT IGNORE para no borrar datos previos.
INSERT IGNORE INTO service_requests (id, client_id, mechanic_id, vehicle_id, status, service_type, description, location, images, estimated_cost, notes)
VALUES
(1, 1, 2, 1, 'Pendiente', 'Mantenimiento básico', 'Cambio de aceite y filtros', 'Calle 123 #45-67', NULL, '50000', 'Cliente estará disponible en la mañana'),
(2, 1, 2, 2, 'En_Proceso', 'Frenos', 'Revisión y cambio de pastillas', 'Av. Central 500', NULL, '120000', 'Prioridad media'),
(3, 2, NULL, 3, 'Pendiente', 'Diagnóstico', 'Revisión general', 'Carrera 10 #20-30', NULL, NULL, 'Llamar antes de llegar');
