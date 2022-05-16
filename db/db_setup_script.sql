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

INSERT INTO users (login, password, role) VALUES ('nagibator', '123', 1);
INSERT INTO users (login, password, role) VALUES ('bulkashmak', '123', 2);