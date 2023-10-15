-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE users
(
    id               bigserial primary key,
    chat_id          bigint       not null,
    first_name       varchar(100) not null,
    last_name        varchar(100) not null,
    email            varchar(100) not null,
    phone            varchar(100) not null,
    duration_counter int default 30,
    animal_id        bigint       not null,
    days_lost_counter bigint not null default 0,
    FOREIGN KEY (animal_id) REFERENCES animal (id)
);
--changeset aMilenkov:2
INSERT INTO users (chat_id, first_name, last_name, email, phone,duration_counter, animal_id)
VALUES (1,
        'TestName',
        'TestLastName',
        'TestEmail',
        'TestPhone',
        1,
        1);