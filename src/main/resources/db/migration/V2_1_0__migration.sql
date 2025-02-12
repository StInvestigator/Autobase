create table IF NOT EXISTS app_user
(
    id           SERIAL PRIMARY KEY,
    name         varchar(36) unique,
    encryted_password varchar(128),
    enabled           boolean
);

create table IF NOT EXISTS role
(
    id SERIAL PRIMARY KEY,
    name varchar(30) unique
);


CREATE TABLE IF NOT EXISTS user_role (
    id SERIAL PRIMARY KEY,
    user_id integer,
    role_id integer,
    FOREIGN KEY (user_id) REFERENCES app_user (id)  ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

insert into app_user (id, name, encryted_password, enabled)
values (1, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', true),
       (2, 'user', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', true);


insert into  role (id, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

insert into  user_role (id, user_id, role_id)
values (1,1,1),
       (2,1,2),
       (3,2,2);
