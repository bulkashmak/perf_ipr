CREATE DATABASE ipr_db ENCODING 'UTF-8';

--Создаем таблицу ролей
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role VARCHAR(10) NOT NULL
);

INSERT INTO roles (role) VALUES ('admin');
INSERT INTO roles (role) VALUES ('user');

--Создаем таблицу пользователей
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(10) UNIQUE NOT NULL,
    password VARCHAR(10) NOT NULL,
    role INTEGER NOT NULL,
    FOREIGN KEY (role) references roles (id)
);

INSERT INTO users (login, password, role) VALUES ('zxc', '123', 1);
INSERT INTO users (login, password, role) VALUES ('bulkashmak', '123', 2);
INSERT INTO users (login, password, role) VALUES ('alexander', '1234', 2);
INSERT INTO users (login, password, role) VALUES ('qwerty', '12345', 2);


--Примеры валидных запросов

SELECT DISTINCT password FROM users;

SELECT login FROM users
UNION
SELECT role FROM roles;

SELECT login FROM users
WHERE role = 1;

SELECT COUNT(users.login), users.password
FROM users
GROUP BY users.password;

SELECT * FROM users
WHERE login IN ('alex', 'bulkashmak');

SELECT * FROM users
WHERE login LIKE '%b%';

SELECT min(password) FROM users;