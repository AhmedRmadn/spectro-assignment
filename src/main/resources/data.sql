-- Insert roles
INSERT INTO role (id, name) VALUES (1, 'USER');
INSERT INTO role (id, name) VALUES (2, 'ADMIN');

-- password = "password123"
INSERT INTO users (id, username, password) VALUES 
(100, 'user1', '$2a$10$C1NJdhzqS1g3YSY7Xu3lFuD23Gy9hz9a6QVeGZ6aeeAojqMw077OO'),
(200, 'admin1', '$2a$10$C1NJdhzqS1g3YSY7Xu3lFuD23Gy9hz9a6QVeGZ6aeeAojqMw077OO');

INSERT INTO users_roles (user_id, roles_id) VALUES 
(100, 1),
(200, 2);
