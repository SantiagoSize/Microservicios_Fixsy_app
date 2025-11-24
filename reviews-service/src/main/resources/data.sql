-- Seed idempotente: usa INSERT IGNORE para no borrar datos previos.
INSERT IGNORE INTO reviews (id, mechanic_id, user_id, rating, comment)
VALUES
(1, 2, 1, 5, 'Excelente servicio, muy puntual y profesional'),
(2, 2, 3, 4, 'Buen trabajo, podría mejorar el tiempo de entrega'),
(3, 1, 2, 3, 'Servicio aceptable, pero el costo fue mayor al estimado');
