INSERT INTO users (id, email, password, first_name, last_name, is_deleted)
VALUES (1, 'test@gmail.com', '12345678', 'Test', 'Test', false);
INSERT INTO users_roles(user_id, role_id)
VALUES (1, 2);