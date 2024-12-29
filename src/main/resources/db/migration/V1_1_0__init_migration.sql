CREATE TABLE destination_points
(
    id       SERIAL PRIMARY KEY,
    name     character varying(30) UNIQUE NOT NULL,
    distance int                          NOT NULL
);

CREATE TABLE goods_types
(
    id                SERIAL PRIMARY KEY,
    name              character varying(50) UNIQUE NOT NULL,
    experience_needed int                          NOT NULL
);

CREATE TABLE requests
(
    id                   SERIAL PRIMARY KEY,
    goods_weight         decimal        NOT NULL,
    destination_point_id integer        NOT NULL,
    goods_type_id        integer        NOT NULL,
    payment              numeric(10, 2) NOT NULL,
    is_taken             boolean        NOT NULL,

    FOREIGN KEY (destination_point_id) REFERENCES destination_points (id) ON DELETE CASCADE,
    FOREIGN KEY (goods_type_id) REFERENCES goods_types (id) ON DELETE CASCADE
);

CREATE TABLE cars
(
    id         SERIAL PRIMARY KEY,
    name       character varying(30) NOT NULL,
    max_weight decimal               NOT NULL,
    is_broken  boolean               NOT NULL,
    is_free    boolean               NOT NULL
);

CREATE TABLE drivers
(
    id         SERIAL PRIMARY KEY,
    name       character varying(30) NOT NULL,
    surname    character varying(30) NOT NULL,
    experience integer               NOT NULL,
    balance    numeric(10, 2)        NOT NULL,
    is_free    boolean               NOT NULL
);

CREATE TABLE trips
(
    id             SERIAL PRIMARY KEY,
    days_remaining int NOT NULL,
    request_id     int NOT NULL,
    driver_id      int NOT NULL,
    car_id         int NOT NULL,
    FOREIGN KEY (request_id) REFERENCES requests (id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES drivers (id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars (id) ON DELETE CASCADE
);

CREATE TABLE completed_trips
(
    id                   SERIAL PRIMARY KEY,
    goods_weight         decimal        NOT NULL,
    destination_point_id integer        NOT NULL,
    goods_type_id        integer        NOT NULL,
    driver_id            int            NOT NULL,
    car_id               int            NOT NULL,
    completion_date      date           NOT NULL,
    payment              numeric(10, 2) NOT NULL,
    FOREIGN KEY (destination_point_id) REFERENCES destination_points (id) ON DELETE CASCADE,
    FOREIGN KEY (goods_type_id) REFERENCES goods_types (id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES drivers (id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars (id) ON DELETE CASCADE
);