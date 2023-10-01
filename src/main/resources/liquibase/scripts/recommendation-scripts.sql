-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE recommendation
(
    title varchar(100) primary key not null,
    text  varchar(255) not null
);