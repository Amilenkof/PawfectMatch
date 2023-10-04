-- liquebase formatted sql


--changeset aMilenkov:1

CREATE TABLE shelter
(
    id bigserial primary key,
    description  varchar(255) not null,
    address varchar(30) not null,
    map bytea not null,
    timing varchar(255) not null,
    contacts_security varchar(255) not null,
    safety text not null);

--changeset aMilenkov:2
ALTER TABLE shelter
    ADD COLUMN animal_type varchar(100) not null default 'Any Animal';

