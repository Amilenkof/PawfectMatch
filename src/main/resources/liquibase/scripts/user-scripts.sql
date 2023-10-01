-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE user
(
    id bigserial primary key,
    chat_id bigint not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(100) not null,
    duration_counter int default 30,
    animal_id bigint not null,
    FOREIGN KEY (animal_id) REFERENCES animal(id)
);