-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE recommendation
(
    id    bigserial primary key,
    title varchar(100) not null,
    text  text not null,
    animal_type varchar(100) not null
);