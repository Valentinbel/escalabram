CREATE TABLE IF NOT EXISTS language (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    label VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO language (id, code, label)
VALUES
    (1, 'en', 'English'),
    (2, 'fr', 'Français');