CREATE TABLE IF NOT EXISTS climb_level (
    id BIGSERIAL PRIMARY KEY,
    code_fr VARCHAR(255)
);

INSERT INTO climb_level (id, code_fr)
VALUES
    (1, '4'),
    (2, '4+'),
    (3, '5A'),
    (4, '5B'),
    (5, '5C'),
    (6, '6A'),
    (7, '6A+'),
    (8, '6B'),
    (9, '6B+'),
    (10, '6C'),
    (11, '6C+'),
    (12, '7A'),
    (13, '7A+'),
    (14, '7B'),
    (15, '7B+'),
    (16, '7C'),
    (17, '7C+'),
    (18, '8A'),
    (19, '8A+'),
    (20, '8B'),
    (21, '8B+'),
    (22, '8C'),
    (23, '8C+'),
    (24, '9A'),
    (25, '9A+'),
    (26, '9B'),
    (27, '9B+'),
    (28, '9C');