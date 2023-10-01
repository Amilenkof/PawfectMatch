-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE feedback
(
    id bigserial primary key,
    first_name  varchar(100) not null,
    email varchar(100) not null,
    last_name  varchar(100) not null,
    phone varchar(100) not null
);