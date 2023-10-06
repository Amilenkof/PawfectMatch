-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE volunteer
(
    id         bigserial primary key,
    first_name varchar(100) not null,
    last_name  varchar(100) not null,
    chat_id    bigint       not null,
    shelter_id bigint       not null,
    FOREIGN KEY (shelter_id) REFERENCES shelter (id)
);