CREATE TABLE gender (
  id BIGSERIAL PRIMARY KEY,
  gender_name VARCHAR(255)
);

INSERT INTO gender (gender_name)
VALUES
    ('male'),
    ('female'),
    ('other')