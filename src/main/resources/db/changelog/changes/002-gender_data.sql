CREATE TABLE gender (
  id INT GENERATED ALWAYS AS IDENTITY,
  gender_name VARCHAR(255)
);

INSERT INTO gender (gender_name)
VALUES
    ('male'),
    ('female'),
    ('other')