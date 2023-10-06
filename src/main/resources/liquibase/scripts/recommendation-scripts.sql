-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE recommendation
(
    id    bigserial primary key,
    title varchar(100) not null,
    text  varchar(255) not null
);