-- Seed idempotente: usa INSERT IGNORE para no borrar datos previos.
INSERT IGNORE INTO vehicles (id, user_id, brand, model, year, plate, color, is_default)
VALUES
(1, 1, 'Toyota', 'Corolla', 2020, 'ABC123', 'Rojo', TRUE),
(2, 1, 'Honda', 'Civic', 2018, 'DEF456', 'Azul', FALSE),
(3, 2, 'Ford', 'Ranger', 2021, 'GHI789', 'Blanco', TRUE);
