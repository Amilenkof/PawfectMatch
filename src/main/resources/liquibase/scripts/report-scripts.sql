-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE report
(
    id bigserial primary key,
    photo bytea not null,
    food varchar(255) not null,
    health varchar(255) not null,
    behaviour varchar(255) not null,
    user_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES users (id)

);