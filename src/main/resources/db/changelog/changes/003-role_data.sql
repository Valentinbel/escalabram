CREATE TABLE role (
  id BIGSERIAL PRIMARY KEY,
  role_name VARCHAR(20)
);

INSERT INTO role (id, role_name)
VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');