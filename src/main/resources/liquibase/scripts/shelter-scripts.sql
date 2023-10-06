-- liquebase formatted sql


--changeset aMilenkov:1

CREATE TABLE shelter
(
    id                bigserial primary key,
    description       varchar(255) not null,
    address           varchar(30)  not null,
    timing            varchar(255) not null,
    contacts_security varchar(255) not null,
    safety            text         not null,
    animal_type       varchar(100) not null
);


