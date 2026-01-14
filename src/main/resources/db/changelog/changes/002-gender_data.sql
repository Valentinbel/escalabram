CREATE TABLE IF NOT EXISTS gender (
    id BIGSERIAL PRIMARY KEY,
    gender_name VARCHAR(255)
);

INSERT INTO gender (id, gender_name)
VALUES
    (1, 'female'),
    (2, 'male'),
    (3, 'other');