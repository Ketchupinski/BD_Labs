DROP TABLE IF EXISTS diagnoses;
DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS doctors_specializations;
DROP TABLE IF EXISTS specializations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;


CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role_id    INT          NOT NULL,
    photo_path VARCHAR(255),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS statuses
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS visits
(
    id        SERIAL PRIMARY KEY,
    user_id   INT  NOT NULL,
    doctor_id INT  NOT NULL,
    date      DATE NOT NULL,
    status_id INT  NOT NULL,
    problem   TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (status_id) REFERENCES statuses (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS specializations
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctors_specializations
(
    doctor_id         INT NOT NULL,
    specialization_id INT NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (specialization_id) REFERENCES specializations (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS diagnoses
(
    id        SERIAL PRIMARY KEY,
    visit_id  INT          NOT NULL,
    name      VARCHAR(255) NOT NULL,
    treatment VARCHAR(255) NOT NULL,
    FOREIGN KEY (visit_id) REFERENCES visits (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_DOCTOR');

INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES ('Barak', 'Obama', 'barak@gmail.com', '123', 1),
       ('Donald', 'Trump', 'donald@gmail.com', '123', 1),
       ('Joe', 'Biden', 'joe@gmail.com', '123', 1),
       ('John', 'Smith', 'smith@gmail.com', '123', 1),
       ('Gregory', 'House', 'house@gmail.com', '123', 2),
       ('James', 'Wilson', 'wilson@gmail.com', '123', 2),
       ('Eric', 'Foreman', 'foreman@gmail.com', '123', 2),
       ('Robert', 'Chase', 'chase@gmail.com', '123', 2);

INSERT INTO specializations (name)
VALUES ('Cardiologist'),
       ('Dentist'),
       ('Dermatologist'),
       ('Endocrinologist'),
       ('Gastroenterologist'),
       ('Neurologist'),
       ('Ophthalmologist'),
       ('Otolaryngologist'),
       ('Pediatrician'),
       ('Psychiatrist'),
       ('Rheumatologist'),
       ('Surgeon');

INSERT INTO doctors_specializations (doctor_id, specialization_id)
VALUES (5, 1),
       (6, 2),
       (7, 5),
       (8, 7);

INSERT INTO statuses (name)
VALUES ('Pending'),
       ('Done'),
       ('Canceled'),
       ('In progress');

INSERT INTO visits (user_id, doctor_id, date, status_id, problem)
VALUES (1, 5, '2020-01-01', 1, 'I have a problem with my heart'),
       (2, 6, '2020-01-02', 1, 'I have a problem with my teeth'),
       (3, 7, '2020-01-03', 1, 'I have a problem with my stomach'),
       (4, 8, '2020-01-04', 1, 'I have a problem with my eyes'),
       (1, 5, '2021-05-16', 1, 'I have a problem with my heart'),
       (2, 6, '2021-05-17', 1, 'I have a problem with my teeth'),
       (3, 7, '2021-05-18', 1, 'I have a problem with my stomach'),
       (4, 8, '2021-05-19', 1, 'I have a problem with my eyes'),
       (2, 5, '2021-05-20', 1, 'I have a problem with my heart'),
       (3, 5, '2022-06-21', 1, 'I have pain in my heart');





