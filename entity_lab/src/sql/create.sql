DROP TABLE IF EXISTS users_tours;
DROP TABLE IF EXISTS tours;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS tours
(
    id         SERIAL PRIMARY KEY,
    price      INTEGER      NOT NULL,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    country    VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_tours
(
    user_id  INT NOT NULL,
    tour_id  INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO users (name, surname)
VALUES ('John', 'Doe'),
       ('Jane', 'Doe'),
       ('John', 'Smith'),
       ('Jane', 'Smith'),
       ('John', 'Black'),
       ('Jane', 'Black'),
       ('John', 'White'),
       ('Jane', 'White'),
       ('John', 'Green'),
       ('Jane', 'Green');

INSERT INTO tours(price, start_date, end_date, country, city)
VALUES (100, '2018-05-05', '2018-06-05', 'Italy', 'Rome'),
       (200, '2018-05-05', '2018-06-05', 'Italy', 'Milan'),
       (300, '2018-05-05', '2018-06-05', 'Italy', 'Venice'),
       (400, '2018-05-05', '2018-06-05', 'Italy', 'Florence'),
       (400, '2018-05-05', '2018-06-05', 'Germany', 'Berlin'),
       (500, '2018-05-05', '2018-06-05', 'Germany', 'Munich'),
       (600, '2018-05-05', '2018-06-05', 'Germany', 'Frankfurt');

INSERT INTO users_tours (user_id, tour_id)
VALUES (1, 1),
       (2, 1),
       (3, 3),
       (5, 4),
       (6, 4),
       (7, 6);


