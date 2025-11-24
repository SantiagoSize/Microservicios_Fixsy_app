-- Seed idempotente: usa INSERT IGNORE para no duplicar ni borrar datos previos.
INSERT IGNORE INTO users (id, name, email, password, phone, role)
VALUES
(1, 'Juan Perez', 'juan@example.com', '123', '111-111', 'CLIENT'),
(2, 'Maria Mecanica', 'maria@example.com', '123', '222-222', 'MECHANIC'),
(3, 'Admin Fixsy', 'admin@example.com', '123', '333-333', 'ADMIN');

INSERT IGNORE INTO mechanic_profiles (user_id, specialty, experience_years, price_per_hour, is_available, average_rating)
VALUES (2, 'Frenos y suspensión', 5, 40, TRUE, 4.8);

INSERT IGNORE INTO addresses (id, user_id, name, address, city, region, postal_code)
VALUES
(1, 1, 'Casa', 'Calle 123 #45-67', 'Ciudad A', 'Region A', '10001'),
(2, 1, 'Trabajo', 'Av. Central 500', 'Ciudad A', 'Region A', '10002'),
(3, 2, 'Taller', 'Carrera 10 #20-30', 'Ciudad B', 'Region B', '20001');
