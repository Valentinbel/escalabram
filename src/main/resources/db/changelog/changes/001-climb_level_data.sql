CREATE TABLE climb_level (
  id INT GENERATED ALWAYS AS IDENTITY,
  code_fr VARCHAR(255)
);

INSERT INTO climb_level (code_fr)
VALUES
('4'),('4+'),
('5A'),('5B'),('5C'),
('6A'),('6A+'),('6B'),('6B+'),('6C'),('6C+'),
('7A'),('7A+'),('7B'),('7B+'),('7C'),('7C+'),
('8A'),('8A+'),('8B'),('8B+'),('8C'),('8C+'),
('9A'),('9A+'),('9B'),('9B+'),('9C')