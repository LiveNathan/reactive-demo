CREATE TABLE IF NOT EXISTS student
(
    id INT auto_increment PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher
(
    id INT auto_increment PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);