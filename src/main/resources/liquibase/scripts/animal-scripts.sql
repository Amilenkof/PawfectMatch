-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE animal
(
    id         bigserial primary key,
    type       varchar(30) not null,
    name       varchar(30) not null,
    is_sick    boolean     not null,
    is_little  boolean     not null,
    shelter_id bigint      not null,
    status     boolean     not null,
    FOREIGN KEY (shelter_id) REFERENCES shelter (id)
);